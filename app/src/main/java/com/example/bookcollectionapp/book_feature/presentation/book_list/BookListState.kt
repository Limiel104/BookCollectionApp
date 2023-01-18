package com.example.bookcollectionapp.book_feature.presentation.book_list

import com.example.bookcollectionapp.book_feature.domain.model.Book
import com.example.bookcollectionapp.book_feature.domain.util.BookOrder

data class BookListState(
    val bookList: List<Book> = emptyList(),
    val bookOrder: BookOrder = BookOrder.TitleAscending(),
    val isSortSectionExpanded: Boolean = false
)
