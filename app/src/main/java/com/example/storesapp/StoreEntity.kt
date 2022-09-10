package com.example.storesapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "StoreEntity") //TODO 2 Convertimos nuestra data class en una tabla
data class StoreEntity(

    @PrimaryKey(autoGenerate = true)  //TODO 3 Le asignamos una primary key
    var id       : Long    = 0,
    var name     : String,
    var phone    : String  = "",
    var website  : String  = "",
    var photoUrl : String,
    var favorite : Boolean = false
)
