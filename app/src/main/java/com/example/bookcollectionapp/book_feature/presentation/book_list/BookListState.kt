package com.example.bookcollectionapp.book_feature.presentation.book_list

import com.example.bookcollectionapp.book_feature.domain.model.Book

data class BookListState(
    val bookList: List<Book> = emptyList()
)
