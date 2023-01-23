package com.example.bookcollectionapp.book_feature.presentation.book_details.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DetailsListItem(
    label: String,
    text: String
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(
                vertical = 7.dp
            )
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = text,
        )
    }
}