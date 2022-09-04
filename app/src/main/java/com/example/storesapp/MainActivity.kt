package com.example.storesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.storesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var  storeAdapter        : StoreAdapter
    private lateinit var  gridLayout          : RecyclerView.LayoutManager
    private lateinit var  binding             : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecycler()

    }

    private fun initRecycler(){
        storeAdapter  = StoreAdapter(mutableListOf()){}
        gridLayout    = GridLayoutManager(this, 2)

        binding.recycler.apply {
            setHasFixedSize(true)
            layoutManager = gridLayout
            adapter = storeAdapter
        }
    }
}