package com.example.bookcollectionapp.book_feature.domain.use_case

import com.example.bookcollectionapp.book_feature.domain.model.Book
import com.example.bookcollectionapp.book_feature.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class GetBooksUseCase(
    private val repository: BookRepository
) {
    operator fun invoke(): Flow<List<Book>> {
        return repository.getBooks()
    }
}