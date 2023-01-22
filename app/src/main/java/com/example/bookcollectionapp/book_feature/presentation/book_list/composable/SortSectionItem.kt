package com.example.bookcollectionapp.book_feature.presentation.book_list.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookcollectionapp.ui.theme.GrayLight
import com.example.bookcollectionapp.ui.theme.Pink

@Composable
fun SortSectionItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        RadioButton(
            selected = selected,
            onClick = onClick,
            modifier = Modifier.size(25.dp),
            colors = RadioButtonDefaults.colors(
                selectedColor = Pink,
                unselectedColor = GrayLight
            ),
        )

        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(start = 3.dp)
        )
    }
}
