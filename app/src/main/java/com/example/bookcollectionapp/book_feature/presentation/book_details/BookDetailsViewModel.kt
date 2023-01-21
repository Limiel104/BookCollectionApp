package com.example.bookcollectionapp.book_feature.presentation.book_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookcollectionapp.book_feature.domain.use_case.BookUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    private val bookUseCase: BookUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _bookDetails = mutableStateOf(BookDetailsState())
    val bookDetails: State<BookDetailsState> = _bookDetails

    init {
        savedStateHandle.get<Int>("bookId")?.let { bookId ->
            if(bookId != -1) {
                viewModelScope.launch {
                    bookUseCase.getBookUseCase(bookId)?.also { book ->
                        _bookDetails.value = bookDetails.value.copy(
                            id = bookId,
                            title = book.title,
                            author = book.author,
                            publisher = book.publisher,
                            genre = book.genre,
                            imagePath = book.imagePath
                        )
                    }
                }
            }
        }
    }
}