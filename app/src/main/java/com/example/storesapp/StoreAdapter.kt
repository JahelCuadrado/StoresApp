package com.example.storesapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.storesapp.databinding.ItemStoreBinding

class StoreAdapter (
    private var stores : MutableList<StoreEntity>,
    private val listener : (StoreEntity) -> Unit
        ) : RecyclerView.Adapter<StoreAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val store = stores[position]
        holder.bind(store)

        holder.itemView.setOnClickListener {
            listener(store)
        }
    }

    override fun getItemCount(): Int = stores.size

    @SuppressLint("NotifyDataSetChanged")
    fun add(storeEntity: StoreEntity) {
        stores.add(storeEntity)
        notifyDataSetChanged()  //Esto cambia actualiza toda la lista, notifyItemChanged(position) actualizaria solo un elemento
    }

    //TODO 12
    @SuppressLint("NotifyDataSetChanged")
    fun setStores(stores: MutableList<StoreEntity>) {
        this.stores = stores  //sustituimos la lista
        notifyDataSetChanged()  //notificamos los cambios
    }


    class ViewHolder(private val binding: ItemStoreBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(storeEntity : StoreEntity){
                with(binding){
                    tvName.text = storeEntity.name
                }
            }
    }


}