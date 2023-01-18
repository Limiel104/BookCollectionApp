package com.example.bookcollectionapp.book_feature.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(
    @PrimaryKey
    val id: Int? = null,
    val dateAdded: Long,
    val title: String,
    val author: String,
    val publisher: String,
    val imagePath: String,
    val imageFileName: String
)
