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
    private var dateAdded: Long? = null

    private val _bookTextFields = mutableStateOf(BookTextFieldsState())
    val bookTextFieldsState: State<BookTextFieldsState> = _bookTextFields

    private val _bookGenre = mutableStateOf(DropdownMenuState())
    val bookGenre: State<DropdownMenuState> = _bookGenre

    private val _bookImagePath = mutableStateOf(BookImagePathState())
    val bookImagePath: State<BookImagePathState> = _bookImagePath

    private val _bookLanguage = mutableStateOf(DropdownMenuState())
    val bookLanguage: State<DropdownMenuState> = _bookLanguage

    private val _bookReadingStatus = mutableStateOf(DropdownMenuState())
    val bookReadingStatus: State<DropdownMenuState> = _bookReadingStatus

    private val _bookRating = mutableStateOf(DropdownMenuState())
    val bookRating: State<DropdownMenuState> = _bookRating

    private val _bookValidationErrorsState = mutableStateOf(ValidationErrorsState())
    val bookValidationErrorsState: State<ValidationErrorsState> = _bookValidationErrorsState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>("bookId")?.let { bookId ->
            if(bookId != -1) {
                viewModelScope.launch {
                    bookUseCases.getBookUseCase(bookId)?.also { book ->
                        bookToEditId = book.id
                        dateAdded = book.dateAdded
                        _bookTextFields.value = bookTextFieldsState.value.copy(
                            title = book.title,
                            author = book.author,
                            publisher = book.publisher
                        )
                        _bookGenre.value = bookGenre.value.copy(
                            selectedOption = book.genre
                        )
                        _bookImagePath.value = bookImagePath.value.copy(
                            imagePath = book.imagePath,
                            imageFileName = book.imageFileName
                        )
                        _bookReadingStatus.value = bookReadingStatus.value.copy(
                            selectedOption = book.readingStatus
                        )
                        _bookRating.value = bookRating.value.copy(
                            selectedOption = book.rating.toString()
                        )
                        _bookLanguage.value = bookLanguage.value.copy(
                            selectedOption = book.language
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditBookEvent) {
        when(event) {
            is AddEditBookEvent.EnteredTitle -> {
                _bookTextFields.value = bookTextFieldsState.value.copy(
                    title= event.value
                )
            }
            is AddEditBookEvent.EnteredAuthor -> {
                _bookTextFields.value = bookTextFieldsState.value.copy(
                    author = event.value
                )
            }
            is AddEditBookEvent.EnteredPublisher -> {
                _bookTextFields.value = bookTextFieldsState.value.copy(
                    publisher = event.value
                )
            }
            is AddEditBookEvent.ChosenGenre -> {
                _bookGenre.value = bookGenre.value.copy(
                    selectedOption = event.value
                )
            }
            is AddEditBookEvent.GenreDropdownMenuStateChanged -> {
                _bookGenre.value = bookGenre.value.copy(
                    isExpanded = event.value
                )
            }
            is AddEditBookEvent.SizeOfGenreTextFieldChanged -> {
                _bookGenre.value = bookGenre.value.copy(
                    textFieldSize = event.value
                )
            }
            is AddEditBookEvent.ChosenLanguage -> {
                _bookLanguage.value = bookLanguage.value.copy(
                    selectedOption = event.value
                )
            }
            is AddEditBookEvent.LanguageDropdownMenuStateChanged -> {
                _bookLanguage.value = bookLanguage.value.copy(
                    isExpanded = event.value
                )
            }
            is AddEditBookEvent.SizeOfLanguageTextFieldChanged -> {
                _bookLanguage.value = bookLanguage.value.copy(
                    textFieldSize = event.value
                )
            }
            is AddEditBookEvent.ChosenReadingStatus -> {
                _bookReadingStatus.value = bookReadingStatus.value.copy(
                    selectedOption = event.value
                )
            }
            is AddEditBookEvent.ReadingStatusDropdownMenuStateChanged -> {
                _bookReadingStatus.value = bookReadingStatus.value.copy(
                    isExpanded = event.value
                )
            }
            is AddEditBookEvent.SizeOfReadingStatusTextFieldChanged -> {
                _bookReadingStatus.value = bookReadingStatus.value.copy(
                    textFieldSize = event.value
                )
            }
            is AddEditBookEvent.ChosenRating -> {
                _bookRating.value = bookRating.value.copy(
                    selectedOption = event.value
                )
            }
            is AddEditBookEvent.RatingDropdownMenuStateChanged -> {
                _bookRating.value = bookRating.value.copy(
                    isExpanded = event.value
                )
            }
            is AddEditBookEvent.SizeOfRatingTextFieldChanged -> {
                _bookRating.value = bookRating.value.copy(
                    textFieldSize = event.value
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
                if (isValidationSuccessful()) {
                    viewModelScope.launch {
                        try {
                            bookUseCases.addBookUseCase(
                                Book(
                                    id = bookToEditId,
                                    dateAdded = System.currentTimeMillis(),
                                    title = bookTextFieldsState.value.title,
                                    author = bookTextFieldsState.value.author,
                                    publisher = bookTextFieldsState.value.publisher,
                                    genre = bookGenre.value.selectedOption,
                                    imagePath = bookImagePath.value.imagePath,
                                    imageFileName = bookImagePath.value.imageFileName,
                                    readingStatus = bookReadingStatus.value.selectedOption,
                                    rating = bookRating.value.selectedOption.toInt(),
                                    language = bookLanguage.value.selectedOption
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
                else {
                    viewModelScope.launch {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = "All fields need to be filled"
                            )
                        )
                    }
                }
            }
        }
    }

    private fun isValidationSuccessful(): Boolean {
        val titleResult = bookUseCases.validateFieldInputUseCase.invoke("Title",bookTextFieldsState.value.title)
        val authorResult = bookUseCases.validateFieldInputUseCase.invoke("Author",bookTextFieldsState.value.author)
        val publisherResult = bookUseCases.validateFieldInputUseCase.invoke("Publisher",bookTextFieldsState.value.publisher)
        val genreResult = bookUseCases.validateFieldInputUseCase.invoke("Genre",bookGenre.value.selectedOption)
        val readingStatusResult = bookUseCases.validateFieldInputUseCase.invoke("Status",bookReadingStatus.value.selectedOption)
        val ratingResult = bookUseCases.validateFieldInputUseCase.invoke("Rating",bookRating.value.selectedOption)
        val languageResult = bookUseCases.validateFieldInputUseCase.invoke("Language",bookLanguage.value.selectedOption)

        val hasError = listOf(
            titleResult,
            authorResult,
            publisherResult,
            genreResult,
            readingStatusResult,
            ratingResult,
            languageResult
        ).any { !it.successful }

        if(hasError) {
            _bookValidationErrorsState.value = bookValidationErrorsState.value.copy(
                titleError = titleResult.errorMessage,
                authorError = authorResult.errorMessage,
                publisherError = publisherResult.errorMessage,
                genreError = genreResult.errorMessage,
                readingStatusError = readingStatusResult.errorMessage,
                ratingError = ratingResult.errorMessage,
                languageError = languageResult.errorMessage
            )
            return false
        }
        return true
    }
}