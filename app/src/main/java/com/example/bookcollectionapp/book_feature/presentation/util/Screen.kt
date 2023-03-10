package com.example.bookcollectionapp.book_feature.presentation.util

sealed class Screen(val route: String) {
    object BookListScreen: Screen("book_list_screen")
    object AddEditBookScreen: Screen("add_edit_book_screen")
    object BookDetailsScreen: Screen("book_details_screen")
}
