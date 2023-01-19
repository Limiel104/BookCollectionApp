package com.example.bookcollectionapp.book_feature.presentation.add_edit_book

import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.geometry.Size

sealed class AddEditBookEvent {

    data class EnteredTitle(val value: String): AddEditBookEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditBookEvent()

    data class EnteredAuthor(val value: String): AddEditBookEvent()
    data class ChangeAuthorFocus(val focusState: FocusState): AddEditBookEvent()

    data class EnteredPublisher(val value: String): AddEditBookEvent()
//    data class ChangePublisherFocus(val focusState: FocusState): AddEditBookEvent()

    data class ChosenGenre(val value: String): AddEditBookEvent()
    data class DropdownMenuStateChanged(val value: Boolean): AddEditBookEvent()
    data class SizeOfTextFieldChanged(val value: Size): AddEditBookEvent()

    data class PickedImage(val value: String): AddEditBookEvent()
    data class PickedNewFileName(val value: String): AddEditBookEvent()

    object SaveBook: AddEditBookEvent()
}
