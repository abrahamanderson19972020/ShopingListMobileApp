package com.abraham.shoppingbookapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    @ColumnInfo("name")
    var itemName:String,
    @ColumnInfo("storename")
    var storeName:String?,
    @ColumnInfo("price")
    var price: Double?,
    @ColumnInfo("image")
    var image:ByteArray?
)
