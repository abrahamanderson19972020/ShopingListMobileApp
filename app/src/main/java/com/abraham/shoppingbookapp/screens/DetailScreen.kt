package com.abraham.shoppingbookapp.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.abraham.shoppingbookapp.model.Item

@Composable
fun DetailScreen(item: Item, deleteFunction:() -> Unit, navController: NavController){
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center){
        Column(horizontalAlignment = Alignment.CenterHorizontally)
        {
            val imageBitmap = item.image?.let { 
                bytes ->  BitmapFactory.decodeByteArray(bytes, 0, bytes.size).asImageBitmap()
            }
            Image(bitmap = imageBitmap!!,
                contentDescription = item.itemName,
                modifier = Modifier
                    .padding(16.dp)
                    .size(300.dp, 200.dp)
            )

            Text(
                text = item.itemName,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.padding(2.dp),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                text = item.storeName.toString(),
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(2.dp),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(3.dp))
            Text(
                text = item.price.toString(),
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(2.dp),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Button(onClick = {
                 deleteFunction()
            }) {
                Text(
                    text = "Delete"
                )
            }
        }
    }
}