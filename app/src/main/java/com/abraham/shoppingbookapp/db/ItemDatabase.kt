package com.abraham.shoppingbookapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abraham.shoppingbookapp.model.Item

@Database(entities = [Item::class], version = 1)
abstract class ItemDatabase: RoomDatabase() {
    abstract fun itemDao():ItemDao
}