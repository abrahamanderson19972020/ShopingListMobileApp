package com.abraham.shoppingbookapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.abraham.shoppingbookapp.model.Item
import com.abraham.shoppingbookapp.screens.AddItemScreen
import com.abraham.shoppingbookapp.screens.DetailScreen
import com.abraham.shoppingbookapp.screens.ItemList
import com.abraham.shoppingbookapp.ui.theme.ShoppingBookAppTheme
import com.abraham.shoppingbookapp.view.ItemViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: ItemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            ShoppingBookAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = "list_screen"
                        ) {
                            composable("list_screen") {
                                viewModel.getItemsList()
                                ItemList(
                                    items = viewModel.itemList.value,
                                    navController = navController
                                )
                            }

                            composable("add_item_screen") {
                                AddItemScreen { item ->
                                    viewModel.saveItem(item)
                                    navController.navigate("list_screen")
                                }
                            }

                            composable(
                                route = "details_screen/{itemId}",
                                arguments = listOf(
                                    navArgument("itemId") {
                                        type = NavType.StringType
                                    }
                                )
                            ) { backStackEntry ->
                                val itemIdString = backStackEntry.arguments?.getString("itemId")
                                val itemId = itemIdString?.toIntOrNull() ?: return@composable
                                viewModel.getItem(itemId)
                                val selectedItem = viewModel.selectedItem.value

                                DetailScreen(item = selectedItem) {
                                    viewModel.deleteItem(selectedItem)
                                    navController.navigate("list_screen")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
