package com.example.bookcollectionapp.book_feature.presentation.add_edit_book

data class BookImagePathState(
    val imagePath: String = "",
    val imageFileName: String = "",
    val isCoverChanged: Boolean = false
)