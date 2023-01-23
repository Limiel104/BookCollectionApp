package com.example.bookcollectionapp.book_feature.presentation.add_edit_book

import androidx.compose.ui.geometry.Size

sealed class AddEditBookEvent {

    data class EnteredTitle(val value: String): AddEditBookEvent()
    data class EnteredAuthor(val value: String): AddEditBookEvent()
    data class EnteredPublisher(val value: String): AddEditBookEvent()

    data class ChosenGenre(val value: String): AddEditBookEvent()
    data class GenreDropdownMenuStateChanged(val value: Boolean): AddEditBookEvent()
    data class SizeOfGenreTextFieldChanged(val value: Size): AddEditBookEvent()

    data class ChosenLanguage(val value: String): AddEditBookEvent()
    data class LanguageDropdownMenuStateChanged(val value: Boolean): AddEditBookEvent()
    data class SizeOfLanguageTextFieldChanged(val value: Size): AddEditBookEvent()

    data class ChosenReadingStatus(val value: String): AddEditBookEvent()
    data class ReadingStatusDropdownMenuStateChanged(val value: Boolean): AddEditBookEvent()
    data class SizeOfReadingStatusTextFieldChanged(val value: Size): AddEditBookEvent()

    data class ChosenRating(val value: String): AddEditBookEvent()
    data class RatingDropdownMenuStateChanged(val value: Boolean): AddEditBookEvent()
    data class SizeOfRatingTextFieldChanged(val value: Size): AddEditBookEvent()

    data class PickedImage(val value: String): AddEditBookEvent()
    data class PickedNewFileName(val value: String): AddEditBookEvent()

    object SaveBook: AddEditBookEvent()
}