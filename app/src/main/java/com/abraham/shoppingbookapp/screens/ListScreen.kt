package com.abraham.shoppingbookapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.abraham.shoppingbookapp.model.Item

@Composable
fun FAB(onClick: () -> Unit){
    FloatingActionButton(onClick = onClick) {
        Icon(Icons.Filled.Add, "Floating Button")
    }
}

@Composable
fun ItemList(items:List<Item>, navController: NavController){
    Scaffold(
        topBar = {},
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FAB {
                navController.navigate("add_item_screen")
            }
        }, content =  {
                innerPadding ->
            LazyColumn(contentPadding = innerPadding, modifier = Modifier.fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primaryContainer)
            )
            {
                items(items){
                    ItemRow(item = it)
                }
            }
        }
    )
}

@Composable
fun ItemRow(item: Item, navController: NavController){
    Column (
        modifier = Modifier.fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .clickable {
                navController.navigate("details_screen/${item.id}")
            }
    ){
        Text(
            text = item.itemName,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(2.dp),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = item.price.toString(),
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(2.dp),
            fontWeight = FontWeight.Bold
        )
    }
}