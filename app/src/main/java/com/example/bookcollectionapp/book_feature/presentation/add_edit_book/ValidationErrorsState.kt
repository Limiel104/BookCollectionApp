package com.example.bookcollectionapp.book_feature.presentation.add_edit_book

data class ValidationErrorsState(
    val titleError: String? = null,
    val authorError: String? = null,
    val publisherError: String? = null,
    val genreError: String? = null,
    val readingStatusError: String? = null,
    val ratingError: String? = null,
    val languageError: String? = null
)