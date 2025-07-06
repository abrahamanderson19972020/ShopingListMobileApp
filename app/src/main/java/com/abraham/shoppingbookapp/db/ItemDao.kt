package com.abraham.shoppingbookapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.abraham.shoppingbookapp.model.Item

@Dao
interface ItemDao {
    @Query("SELECT name, id FROM item")
    suspend fun getItemWithNameandId():List<Item>

    @Query("SELECT * FROM item WHERE id = :id")
    suspend fun getItemById(id:Int):Item

    @Insert
    suspend fun saveItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)
}