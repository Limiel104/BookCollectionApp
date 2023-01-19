package com.example.bookcollectionapp.book_feature.presentation.add_edit_book

import androidx.compose.ui.geometry.Size

data class DropdownMenuState(
    val selectedOption: String = "",
    val isExpanded: Boolean = false,
    val textFieldSize: Size = Size.Zero
)