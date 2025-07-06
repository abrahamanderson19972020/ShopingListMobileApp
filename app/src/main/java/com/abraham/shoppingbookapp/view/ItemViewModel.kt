package com.abraham.shoppingbookapp.view

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.abraham.shoppingbookapp.db.ItemDatabase
import com.abraham.shoppingbookapp.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemViewModel(application: Application):AndroidViewModel(application) {
    private val db = Room.databaseBuilder(
        getApplication(),
        ItemDatabase::class.java, "Items"
    ).build()

    private val itemDao = db.itemDao()
    val itemList = mutableStateOf<List<Item>>(listOf())
    val selectedItem = mutableStateOf<Item>(Item(1, "", "",0.0, image =  ByteArray(1)))

    /*
    init {
        getItemsList()
    }
    */

    fun getItemsList(){
        viewModelScope.launch(Dispatchers.IO) {
            itemList.value = itemDao.getItemWithNameandId()
        }
    }

    fun getItem(id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            val item = itemDao.getItemById(id)
            item?.let {
                selectedItem.value = it
            }
        }
    }

    fun saveItem(item: Item){
        viewModelScope.launch(Dispatchers.IO) {
            itemDao.saveItem(item)
        }
    }

    fun deleteItem(item:Item){
        viewModelScope.launch(Dispatchers.IO) {
            itemDao.deleteItem(item)
        }
    }
}