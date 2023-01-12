package com.example.bookcollectionapp.book_feature.domain.use_case

import com.example.bookcollectionapp.book_feature.domain.model.Book

data class BookUseCases (
    val getBooksUseCase: GetBooksUseCase,
    val deleteBookUseCase: DeleteBookUseCase,
    val addBookUseCase: AddBookUseCase
)