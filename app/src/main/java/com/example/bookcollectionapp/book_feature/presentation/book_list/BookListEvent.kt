package com.example.bookcollectionapp.book_feature.presentation.book_list

import com.example.bookcollectionapp.book_feature.domain.model.Book
import com.example.bookcollectionapp.book_feature.domain.util.BookOrder

sealed class BookListEvent {
    data class DeleteBook(val book: Book): BookListEvent()
    data class Order(val bookOrder: BookOrder): BookListEvent()
    object RestoreBook: BookListEvent()
    object ToggleSortSection: BookListEvent()
}
