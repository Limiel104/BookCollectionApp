package com.example.bookcollectionapp.book_feature.presentation.book_list

import com.example.bookcollectionapp.book_feature.domain.model.Book

sealed class BookListEvent {
    data class DeleteBook(val book: Book): BookListEvent()
    object RestoreBook: BookListEvent()
}
