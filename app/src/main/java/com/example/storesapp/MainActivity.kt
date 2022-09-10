package com.example.storesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.storesapp.databinding.ActivityMainBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity(), MainAux { //TODO fragment 10

    private lateinit var  storeAdapter        : StoreAdapter
    private lateinit var  gridLayout          : RecyclerView.LayoutManager
    private lateinit var  binding             : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.btnSave.setOnClickListener {
//            clickButtonSave()
//        }

        binding.fabCrearTienda.setOnClickListener { //TODO fragment 2
            launchEditFragment()
        }

        initRecycler()
    }

    private fun launchEditFragment(args : Bundle? = null) { //TODO fragment 3
        val fragment = EditStoreFragment()  //instancia de la clase kotlin del fragmento
        if (args != null) fragment.arguments = args

        val fragmentManager = supportFragmentManager  //creamos una instancia de fragment manager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.container_main, fragment)  //añadimos nuestro fragmente al viewgroup de nuestra vista principal
        fragmentTransaction.addToBackStack(null)  //desvincular el fragment de la activity
        fragmentTransaction.commit()  //aplicamos los cambios

        hideButton(true)
    }

    fun getAdapter(): StoreAdapter {
        return storeAdapter
    }


//    private fun clickButtonSave(){
//        if(binding.etName.text.isNotEmpty()) {
//            val storeEntity = StoreEntity(name = binding.etName.text.toString().trim())
//
//            Thread{  //TODO 8 hacemos la insercion en un hilo diferente al principal
//                StoreApplication.database.storeDao().addStore(storeEntity) //TODO 6 Añadimos un registro en BBDD
//            }.start()
//
//
//            storeAdapter.add(storeEntity)
//            binding.etName.setText("")
//        }else{
//            Toast.makeText(this, "Por favor, escribe el nombre de la tienda.", Toast.LENGTH_LONG).show()
//        }
//    }

    private fun initRecycler(){
        storeAdapter  = StoreAdapter(mutableListOf(), ::listener, ::onFavoriteStore, ::onDeleteStore) //TODO 16
        gridLayout    = GridLayoutManager(this, 2)
        getStores()  //TODO 13
        binding.recycler.apply {
            setHasFixedSize(true)
            layoutManager = gridLayout
            adapter = storeAdapter
        }}


    private fun onDeleteStore(store: StoreEntity){
        doAsync {
            StoreApplication.database.storeDao().deleteStore(store)
            uiThread {
                storeAdapter.delete(store)
            }}}



    private fun listener(storeId: Long){
        val args = Bundle()
        args.putLong("arg_id", storeId)

        launchEditFragment(args)
    }



    private fun onFavoriteStore(store: StoreEntity){  //TODO 17
        store.favorite = !store.favorite
        doAsync {
            StoreApplication.database.storeDao().updateStore(store)
            uiThread {  //con esto volvemos al hilo principal, volvemos al hilo principal porque vamos a actualizar la UI
                storeAdapter.update(store)  //TODO 18
            }}}




    //TODO 11
    private fun getStores(){
        doAsync{  //Subproceso con anko
            val stores = StoreApplication.database.storeDao().getAllStores()  //hacemos consulta de las bbdd y guardamos lo que devuelve
            uiThread {  //Esto ocurrirá cuando finalice el subproceso
                storeAdapter.setStores(stores)  //llamamos al metodo setStores del adapter (aun no existe) y le pasamos las tiendas
            }}}



    override fun hideButton(estado: Boolean) { //TODO fragment 11
        if (estado) binding.fabCrearTienda.hide()
        else binding.fabCrearTienda.show()
    }






}