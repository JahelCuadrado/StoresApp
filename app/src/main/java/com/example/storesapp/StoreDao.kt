package com.example.storesapp

import androidx.room.*

@Dao //TODO 4 Aqui vamos a crear nuestras consultas de bbdd
interface StoreDao {
    @Query("SELECT * FROM StoreEntity")
    fun getAllStores() : MutableList<StoreEntity>

    @Query("SELECT * FROM StoreEntity WHERE id = :id")  //Con los 2 puntos indico que me refiero a la variable id que llega por parámetro
    fun getById(id: Int) : StoreEntity

    @Insert
    fun addStore(storeEntity: StoreEntity)

    @Update
    fun updateStore(storeEntity: StoreEntity)

    @Delete
    fun deleteStore(storeEntity: StoreEntity)
}