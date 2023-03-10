package com.example.bookcollectionapp.book_feature.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bookcollectionapp.book_feature.presentation.add_edit_book.composable.AddEditBookScreen
import com.example.bookcollectionapp.book_feature.presentation.book_details.composable.BookDetailsScreen
import com.example.bookcollectionapp.book_feature.presentation.book_list.composable.BookListScreen
import com.example.bookcollectionapp.book_feature.presentation.util.Screen
import com.example.bookcollectionapp.ui.theme.BookCollectionAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookCollectionAppTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.BookListScreen.route
                    ) {
                        composable(
                            route = Screen.BookListScreen.route
                        ) {
                            BookListScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditBookScreen.route + "?bookId={bookId}",
                            arguments = listOf(
                                navArgument(
                                    name = "bookId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            AddEditBookScreen(navController = navController)
                        }
                        composable(
                            route = Screen.BookDetailsScreen.route + "bookId={bookId}",
                            arguments = listOf(
                                navArgument(
                                    name = "bookId"
                                ) {
                                    type = NavType.IntType
                                }
                            )
                        ) {
                            BookDetailsScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
