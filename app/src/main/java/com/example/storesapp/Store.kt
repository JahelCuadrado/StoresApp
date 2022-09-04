package com.example.storesapp

data class Store(
    var id       : Long    = 0,
    var name     : String,
    var phone    : String  = "",
    var website  : String  = "",
    var favorite : Boolean = false
)
