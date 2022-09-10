package com.example.storesapp

import androidx.room.*

@Dao //TODO 4 Aqui vamos a crear nuestras consultas de bbdd
interface StoreDao {
    @Query("SELECT * FROM StoreEntity")
    fun getAllStores() : MutableList<StoreEntity>

    @Query("SELECT * FROM StoreEntity WHERE id = :id")  //Con los 2 puntos indico que me refiero a la variable id que llega por parámetro
    fun getById(id: Long) : StoreEntity

    @Insert
    fun addStore(storeEntity: StoreEntity) : Long

    @Update
    fun updateStore(storeEntity: StoreEntity)

    @Delete
    fun deleteStore(storeEntity: StoreEntity)
}