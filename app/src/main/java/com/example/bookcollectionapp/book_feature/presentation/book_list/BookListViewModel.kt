package com.example.bookcollectionapp.book_feature.presentation.book_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookcollectionapp.book_feature.domain.model.Book
import com.example.bookcollectionapp.book_feature.domain.use_case.BookUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val bookUseCases: BookUseCases
): ViewModel() {

    private val _state = mutableStateOf(BookListState())
    val state: State<BookListState> = _state

    private var deletedBook: Book? = null

    private var getBookListJob: Job? = null

    init {
        getBooks()
    }

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

    private fun getBooks() {
        getBookListJob?.cancel()
        getBookListJob = bookUseCases.getBooksUseCase().onEach { bookList ->
            _state.value = state.value.copy(
                bookList = bookList
            )
        }.launchIn(viewModelScope)
    }
}