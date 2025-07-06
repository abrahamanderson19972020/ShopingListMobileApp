package com.abraham.shoppingbookapp.screens

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.text.ListFormatter.Width
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.abraham.shoppingbookapp.R
import com.abraham.shoppingbookapp.model.Item
import java.io.ByteArrayOutputStream

@Composable
fun AddItemScreen(saveFunction: (item: Item) -> Unit, navController: NavController) {
    val itemName = remember { mutableStateOf("") }
    val storeName = remember { mutableStateOf("") }
    val itemPrice = remember { mutableStateOf("") }
    val selectedImageUri = remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Image Picker
            ImagePicker(onImageSelected = { uri ->
                selectedImageUri.value = uri
            })

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = itemName.value,
                placeholder = { Text("Item Name") },
                onValueChange = { itemName.value = it }
            )

            TextField(
                value = storeName.value,
                placeholder = { Text("Store Name") },
                onValueChange = { storeName.value = it }
            )

            TextField(
                value = itemPrice.value,
                placeholder = { Text("Price") },
                onValueChange = { itemPrice.value = it },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                val imageByteArray = selectedImageUri.value?.let {
                    resizeImage(context = context, uri = it, maxHeight = 600, maxWidth = 400)
                } ?: ByteArray(0)

                val itemToSave = Item(
                    id = 0,
                    itemName = itemName.value,
                    storeName = storeName.value.ifBlank { null },
                    price = itemPrice.value.toDoubleOrNull() ?: 0.0,
                    image = imageByteArray
                )

                saveFunction(itemToSave)
            }) {
                Text("Save")
            }
        }
    }
}


@Composable
fun ImagePicker(onImageSelected: (Uri?) -> Unit) {
    val selectedImageUri = remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        android.Manifest.permission.READ_MEDIA_IMAGES
    } else {
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedImageUri.value = uri
        onImageSelected(uri) // Notify the parent
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            galleryLauncher.launch("image/*")
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_LONG).show()
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        selectedImageUri.value?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(model = uri),
                contentDescription = "Selected Image",
                modifier = Modifier
                    .size(300.dp, 200.dp)
                    .padding(16.dp)
            )
        } ?: Image(
            painter = painterResource(id = R.drawable.selectimage),
            contentDescription = "Select Image",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .size(300.dp, 200.dp)
                .clickable {
                    if (ContextCompat.checkSelfPermission(context, permission)
                        == PackageManager.PERMISSION_GRANTED
                    ) {
                        galleryLauncher.launch("image/*")
                    } else {
                        permissionLauncher.launch(permission)
                    }
                }
        )
    }
}

fun resizeImage(context: Context, uri:Uri, maxWidth: Int, maxHeight:Int):ByteArray?{
   return try {
       val inputStream = context.contentResolver.openInputStream(uri)
       val originalBitmap = BitmapFactory.decodeStream(inputStream)
       val ratio = originalBitmap.width.toFloat() / originalBitmap.height.toFloat()
       var width = maxWidth
       var height = (width / ratio).toInt()
       if(height > maxHeight){
           height = maxHeight
           width = (height * ratio).toInt()
       }
       val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, width,height, false)
       val byteArrayOutputStream = ByteArrayOutputStream()
       resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
       byteArrayOutputStream.toByteArray()
   } catch (e: Exception){
       e.printStackTrace()
       null
   }
}
