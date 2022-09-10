package com.example.storesapp

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.storesapp.databinding.FragmentEditStoreBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class EditStoreFragment : Fragment() {

    private lateinit var binding      : FragmentEditStoreBinding
    private          var mainActivity : MainActivity?            = null
    private          var isEditMode   : Boolean                  = false
    private          var storeEntity  : StoreEntity?             = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {  //Cambiar View por View? si algo falla
        binding = FragmentEditStoreBinding.inflate(inflater, container, false)  //TODO Fragment: inflar el fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {  // //TODO fragment 3 metodo que salta cuando todas las vistas se han finalizado de crear, lo hacemos aqui para asegurarnos que ninguna referencia que hagamos sea nula
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getLong("arg_id", 0)
        if (id!=null && id!=0L){
            Toast.makeText(activity, "$id", Toast.LENGTH_SHORT).show()
            isEditMode = true
            getStore(id)
        }

        mainActivity = activity as? MainActivity
        mainActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)  //activar flecha hacia atras en el fragment
        mainActivity?.supportActionBar?.title = "Crear tienda"  //poner titulo a la barra del fragment

        setHasOptionsMenu(true) //TODO fragment 5

        binding.etUrl.addTextChangedListener {
            Glide.with(this)
                .load(binding.etUrl.text.toString())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.imgStore)
        }
    }

    private fun getStore(id: Long) {
        doAsync {
            storeEntity = StoreApplication.database.storeDao().getById(id)
            uiThread {
                if (storeEntity!=null){
                    setUiStore(storeEntity!!)
                }
            }
        }

    }

    private fun setUiStore(storeEntity: StoreEntity) {
        with(binding){
            etName.setText(storeEntity.name)
            etPhone.setText(storeEntity.phone)
            etWebsite.setText(storeEntity.website)
            etUrl.setText(storeEntity.photoUrl)
            Glide.with(this@EditStoreFragment)
                .load(storeEntity.photoUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(imgStore)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {  //TODO fragment 6
        inflater.inflate(R.menu.manu_save, menu) //inflamos el menu
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {  //TODO fragment 7
        return when(item.itemId){
            android.R.id.home -> {
                hideKeyboard()
                mainActivity?.onBackPressed()
                true
            }
            R.id.action_save -> {
                Toast.makeText(activity, "Tienda agragada", Toast.LENGTH_SHORT).show()
                guardarTiendaBbdd()
                hideKeyboard()
                true
            }
            else -> {
                Toast.makeText(activity, "No pasó nada :)", Toast.LENGTH_SHORT).show()
                true
            }}}
            //return super.onOptionsItemSelected(item) }


    private fun guardarTiendaBbdd(){
        val store =
            StoreEntity( name = binding.etName.text.toString().trim(),
                         phone = binding.etPhone.text.toString().trim(),
                        website = binding.etWebsite.text.toString().trim(),
                        photoUrl = binding.etUrl.text.toString().trim())

        doAsync {
            store.id = StoreApplication.database.storeDao().addStore(store)
            uiThread {
                val storeAdapter = mainActivity?.getAdapter()
                storeAdapter?.add(store)
            }}}


    private fun hideKeyboard(){
        val inputMethodManager = mainActivity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }



    override fun onDestroy() { //TODO fragment 8
        mainActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mainActivity?.supportActionBar?.title = "Stores"
        setHasOptionsMenu(false)
        mainActivity?.hideButton(false)
        super.onDestroy()
    }


}