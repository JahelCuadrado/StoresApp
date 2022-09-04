package com.example.storesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.storesapp.databinding.ItemStoreBinding

class StoreAdapter (
        private val stores : List<Store>,
        private val listener : (Store) -> Unit
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


    class ViewHolder(private val binding: ItemStoreBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(store : Store){
                with(binding){
                    tvName.text = store.name
                }
            }
    }


}