package com.example.storesapp

import android.app.Application
import androidx.room.Room

//TODO 5
class StoreApplication : Application() {

    companion object{
        lateinit var database: StoreDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            this,
                   StoreDatabase::class.java,
            "StoreDatabase")
                   .build()
    }
}