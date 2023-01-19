package com.example.bookcollectionapp.book_feature.presentation.book_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookcollectionapp.book_feature.domain.model.Book
import com.example.bookcollectionapp.book_feature.domain.use_case.BookUseCases
import com.example.bookcollectionapp.book_feature.domain.util.BookOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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
    private var searchQueryJob: Job? = null

    init {
        getBooks(BookOrder.TitleAscending())
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
            is BookListEvent.Order -> {
                if(state.value.bookOrder::class.java == event.bookOrder::class.java) {
                    return
                }
                getBooks(event.bookOrder)
            }
            is BookListEvent.ToggleSortSection -> {
                _state.value = state.value.copy(
                    isSortSectionExpanded = !state.value.isSortSectionExpanded
                )
            }
            is BookListEvent.OnSearchQueryChange -> {
                _state.value = state.value.copy(
                    searchQuery = event.query
                )
                searchQueryJob?.cancel()
                searchQueryJob = viewModelScope.launch {
                    delay(500L)
                    getBooks(_state.value.bookOrder)
                }
            }
        }
    }

    private fun getBooks(
        bookOrder: BookOrder,
        query: String = _state.value.searchQuery.lowercase(),
        filter: String = "Mystery"
    ) {
        getBookListJob?.cancel()
        getBookListJob = bookUseCases.getBooksUseCase(bookOrder,query,filter).onEach { bookList ->
            _state.value = state.value.copy(
                bookList = bookList,
                bookOrder = bookOrder
            )
        }.launchIn(viewModelScope)
    }
}