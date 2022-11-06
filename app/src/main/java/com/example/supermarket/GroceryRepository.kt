package com.example.supermarket

class GroceryRepository(private val db: GroceryDatabase) {

    suspend fun insert(item: GroceryItem) = db.getGroceryDao().insertItem(item)
    suspend fun delete(item: GroceryItem) = db.getGroceryDao().deleteItem(item)

    fun getAllItems() = db.getGroceryDao().getAllItems()
}