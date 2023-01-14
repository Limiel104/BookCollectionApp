package com.example.bookcollectionapp.book_feature.domain.use_case

import com.example.bookcollectionapp.book_feature.domain.model.Book
import com.example.bookcollectionapp.book_feature.domain.repository.BookRepository

class GetBookUseCase(
    private val repository: BookRepository
) {
    suspend operator fun invoke(id: Int): Book? {
        return repository.getBookById(id)
    }
}