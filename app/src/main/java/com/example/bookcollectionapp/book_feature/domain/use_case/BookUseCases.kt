package com.example.bookcollectionapp.book_feature.domain.use_case

data class BookUseCases (
    val getBooksUseCase: GetBooksUseCase,
    val deleteBookUseCase: DeleteBookUseCase,
    val addBookUseCase: AddBookUseCase,
    val getBookUseCase: GetBookUseCase,
    val validateFieldInputUseCase: ValidateFieldInputUseCase
)