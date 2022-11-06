package com.example.supermarket

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
abstract class GroceryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertItem(item: GroceryItem)

    @Delete
    abstract fun deleteItem(item: GroceryItem)

    @Query("SELECT * FROM grocery_items")
    abstract fun getAllItems(): LiveData<List<GroceryItem>>
}