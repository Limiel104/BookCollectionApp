package com.example.bookcollectionapp.book_feature.presentation.add_edit_book

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookcollectionapp.book_feature.domain.model.Book
import com.example.bookcollectionapp.book_feature.domain.use_case.BookUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditBookViewModel @Inject constructor(
    private val bookUseCases: BookUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private var bookToEditId: Int? = null

    private val _bookTitle = mutableStateOf(BookTextFieldState(
        hint = "Title"
    ))
    val bookTitle: State<BookTextFieldState> = _bookTitle

    private val _bookAuthor = mutableStateOf(BookTextFieldState(
        hint = "Author"
    ))
    val bookAuthor: State<BookTextFieldState> = _bookAuthor

    private val _bookPublisher = mutableStateOf(BookTextFieldState())
    val bookPublisher: State<BookTextFieldState> = _bookPublisher

    private val _bookImagePath = mutableStateOf(BookImagePathState())
    val bookImagePath: State<BookImagePathState> = _bookImagePath

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>("bookId")?.let { bookId ->
            if(bookId != -1) {
                viewModelScope.launch {
                    bookUseCases.getBookUseCase(bookId)?.also { book ->
                        bookToEditId = book.id
                        _bookTitle.value = bookTitle.value.copy(
                            text = book.title,
                            isHintVisible = false
                        )
                        _bookAuthor.value = bookAuthor.value.copy(
                            text = book.author,
                            isHintVisible = false
                        )
                        _bookPublisher.value = bookPublisher.value.copy(
                            text = book.publisher,
//                            isHintVisible = false
                        )
                        _bookImagePath.value = bookImagePath.value.copy(
                            imagePath = book.imagePath,
                            imageFileName = book.imageFileName
                        )
                    }
                }
            }

        }
    }

    fun onEvent(event: AddEditBookEvent) {
        when(event) {
            is AddEditBookEvent.EnteredTitle -> {
                _bookTitle.value = bookTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditBookEvent.ChangeTitleFocus -> {
                _bookTitle.value = bookTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && bookTitle.value.text.isBlank()
                )
            }
            is AddEditBookEvent.EnteredAuthor -> {
                _bookAuthor.value = bookAuthor.value.copy(
                    text = event.value
                )
            }
            is AddEditBookEvent.ChangeAuthorFocus -> {
                _bookAuthor.value = bookAuthor.value.copy(
                    isHintVisible = !event.focusState.isFocused && bookAuthor.value.text.isBlank()
                )
            }
            is AddEditBookEvent.EnteredPublisher -> {
                _bookPublisher.value = bookPublisher.value.copy(
                    text = event.value
                )
            }
            is AddEditBookEvent.PickedImage -> {
                _bookImagePath.value = bookImagePath.value.copy(
                    imagePath = event.value,
                    isCoverChanged = true
                )
            }
            is AddEditBookEvent.PickedNewFileName -> {
                _bookImagePath.value = bookImagePath.value.copy(
                    imageFileName = event.value
                )
            }
            is AddEditBookEvent.SaveBook -> {
                viewModelScope.launch {
                    try {
                        bookUseCases.addBookUseCase(
                            Book(
                                id = bookToEditId,
                                title = bookTitle.value.text,
                                author = bookAuthor.value.text,
                                publisher = bookPublisher.value.text,
                                imagePath = bookImagePath.value.imagePath,
                                imageFileName = bookImagePath.value.imageFileName
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveBook)
                    } catch (e: Exception) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Error occurred - couldn't save book"
                            )
                        )
                    }
                }
            }
        }
    }
}