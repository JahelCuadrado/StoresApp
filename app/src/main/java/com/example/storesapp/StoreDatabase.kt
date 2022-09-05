package com.example.storesapp

import androidx.room.Database
import androidx.room.RoomDatabase

//TODO 4, Creamos la base de datos
@Database(entities = arrayOf(StoreEntity::class), version = 1)
abstract class StoreDatabase : RoomDatabase(){

    abstract fun storeDao(): StoreDao

}