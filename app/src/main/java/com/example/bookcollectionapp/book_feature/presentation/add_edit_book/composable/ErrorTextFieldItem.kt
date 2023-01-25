package com.example.bookcollectionapp.book_feature.presentation.add_edit_book.composable

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun ErrorTextFieldItem(
    errorMessage: String
) {
    Text(
        text = errorMessage,
        fontSize = 13.sp,
        color = MaterialTheme.colors.error,
        textAlign = TextAlign.Start,
    )
}