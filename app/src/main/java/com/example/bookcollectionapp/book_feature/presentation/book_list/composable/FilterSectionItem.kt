package com.example.bookcollectionapp.book_feature.presentation.book_list.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
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
        color = if (isSelected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.primaryVariant,
        border = if (isSelected) BorderStroke(1.dp, MaterialTheme.colors.secondary) else BorderStroke(1.dp, MaterialTheme.colors.primaryVariant)
    ) {
        Row(
            modifier = Modifier
                .testTag(text)
                .toggleable(
                    value = isSelected,
                    onValueChange = {
                        onClick(text)
                    }
                )
        ) {
            Text(
                text = text,
                color = if (isSelected) MaterialTheme.colors.onSecondary else MaterialTheme.colors.onPrimary,
                modifier = Modifier.padding(7.dp,4.dp)
            )
        }
    }
}