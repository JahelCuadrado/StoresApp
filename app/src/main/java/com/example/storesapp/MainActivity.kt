package com.example.storesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.storesapp.databinding.ActivityMainBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {

    private lateinit var  storeAdapter        : StoreAdapter
    private lateinit var  gridLayout          : RecyclerView.LayoutManager
    private lateinit var  binding             : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            if(binding.etName.text.isNotEmpty()) {
                val storeEntity = StoreEntity(name = binding.etName.text.toString().trim())

                Thread{  //TODO 8 hacemos la insercion en un hilo diferente al principal
                    StoreApplication.database.storeDao().addStore(storeEntity) //TODO 6 Añadimos un registro en BBDD
                }.start()


                storeAdapter.add(storeEntity)
                binding.etName.setText("")
            }else{
                Toast.makeText(this, "Por favor, escribe el nombre de la tienda.", Toast.LENGTH_LONG).show()
            }
        }

        initRecycler()
    }

    private fun initRecycler(){
        storeAdapter  = StoreAdapter(mutableListOf(), ::listener, ::onFavoriteStore, ::onDeleteStore) //TODO 16
        gridLayout    = GridLayoutManager(this, 2)
        getStores()  //TODO 13
        binding.recycler.apply {
            setHasFixedSize(true)
            layoutManager = gridLayout
            adapter = storeAdapter
        }
    }


    private fun onDeleteStore(store: StoreEntity){
        doAsync {
            StoreApplication.database.storeDao().deleteStore(store)
            uiThread {
                storeAdapter.delete(store)
            }
        }

    }


    private fun listener(store: StoreEntity){

    }


    private fun onFavoriteStore(store: StoreEntity){  //TODO 17
        store.favorite = !store.favorite
        doAsync {
            StoreApplication.database.storeDao().updateStore(store)
            uiThread {  //con esto volvemos al hilo principal, volvemos al hilo principal porque vamos a actualizar la UI
                storeAdapter.update(store)  //TODO 18
            }
        }
    }


    //TODO 11
    private fun getStores(){
        doAsync{  //Subproceso con anko
            val stores = StoreApplication.database.storeDao().getAllStores()  //hacemos consulta de las bbdd y guardamos lo que devuelve
            uiThread {  //Esto ocurrirá cuando finalice el subproceso
                storeAdapter.setStores(stores)  //llamamos al metodo setStores del adapter (aun no existe) y le pasamos las tiendas
            }
        }

    }
}