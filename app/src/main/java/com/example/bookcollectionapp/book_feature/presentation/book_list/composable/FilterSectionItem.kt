package com.example.bookcollectionapp.book_feature.presentation.book_list.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FilterSectionItem(
    text: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onClick: (String) -> Unit
) {
    Surface(
        modifier = modifier.padding(end = 8.dp),
        elevation = 8.dp,
        shape = CircleShape,
        color = if (isSelected) Color.Black else Color.LightGray,
        border = BorderStroke(1.dp, Color.Black)

    ) {
        Row(
            modifier = Modifier
                .toggleable(
                    value = isSelected,
                    onValueChange = {
                        onClick(text)
                    }
                )
        ) {
            Text(
                text = text,
                color = Color.White,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}