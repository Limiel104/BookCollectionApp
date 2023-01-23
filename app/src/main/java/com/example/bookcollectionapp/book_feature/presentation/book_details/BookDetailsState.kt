package com.example.bookcollectionapp.book_feature.presentation.book_details

data class BookDetailsState (
    val id: Int = -1,
    val dateAdded: Long = -1,
    val title: String = "",
    val author: String = "",
    val publisher: String = "",
    val genre: String = "",
    val imagePath: String = "",
    val readingStatus: String = "",
    val rating: Int = -1,
    val language: String = ""
)