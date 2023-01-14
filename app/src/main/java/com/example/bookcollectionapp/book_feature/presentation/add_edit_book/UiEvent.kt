package com.example.bookcollectionapp.book_feature.presentation.add_edit_book

sealed class UiEvent {
    data class ShowSnackbar(val message: String): UiEvent()
    object SaveBook: UiEvent()
}
