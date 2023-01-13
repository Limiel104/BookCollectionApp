package com.example.bookcollectionapp.book_feature.presentation.util

sealed class Screen(val route: String) {
    object BookListScreen: Screen("book_list_screen")
}
