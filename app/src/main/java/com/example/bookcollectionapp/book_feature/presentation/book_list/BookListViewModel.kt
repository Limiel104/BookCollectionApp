package com.example.bookcollectionapp.book_feature.presentation.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookcollectionapp.book_feature.domain.model.Book
import com.example.bookcollectionapp.book_feature.domain.use_case.BookUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val bookUseCases: BookUseCases
): ViewModel() {

    init {
        getBooks()
    }

    private var deletedBook: Book? = null

    fun onEvent(event: BookListEvent) {
        when(event) {
            is BookListEvent.DeleteBook -> {
                viewModelScope.launch {
                    bookUseCases.deleteBookUseCase(event.book)
                    deletedBook = event.book
                }
            }
            is BookListEvent.RestoreBook -> {
                viewModelScope.launch {
                    bookUseCases.addBookUseCase(deletedBook ?: return@launch)
                    deletedBook = null
                }
            }
        }
    }

    private fun getBooks(){
        bookUseCases.getBooksUseCase()
    }
}