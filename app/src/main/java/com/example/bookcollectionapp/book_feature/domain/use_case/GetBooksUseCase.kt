package com.example.bookcollectionapp.book_feature.domain.use_case

import com.example.bookcollectionapp.book_feature.domain.model.Book
import com.example.bookcollectionapp.book_feature.domain.repository.BookRepository
import com.example.bookcollectionapp.book_feature.domain.util.BookOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetBooksUseCase(
    private val repository: BookRepository
) {
    operator fun invoke(
        bookOrder: BookOrder = BookOrder.TitleAscending(),
        query: String
    ): Flow<List<Book>> {
        if (query == "") {
            return repository.getBooks().map { books ->
                when (bookOrder) {
                    is BookOrder.DateAscending -> books.sortedBy { it.dateAdded }
                    is BookOrder.DateDescending -> books.sortedByDescending { it.dateAdded }
                    is BookOrder.TitleAscending -> books.sortedBy { it.title.lowercase() }
                    is BookOrder.TitleDescending -> books.sortedByDescending { it.title.lowercase() }
                }
            }
        }
        else {
            return flow {
                emit(repository.searchBookList(query))
            }.map { books ->
                when (bookOrder) {
                    is BookOrder.DateAscending -> books.sortedBy { it.dateAdded }
                    is BookOrder.DateDescending -> books.sortedByDescending { it.dateAdded }
                    is BookOrder.TitleAscending -> books.sortedBy { it.title.lowercase() }
                    is BookOrder.TitleDescending -> books.sortedByDescending { it.title.lowercase() }
                }
            }
        }

    }
}