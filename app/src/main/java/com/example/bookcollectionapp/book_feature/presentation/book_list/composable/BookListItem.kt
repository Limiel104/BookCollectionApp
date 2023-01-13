package com.example.bookcollectionapp.book_feature.presentation.book_list.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookcollectionapp.book_feature.domain.model.Book

@Composable
fun BookListItem(
    book: Book,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.background(Color.LightGray),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = Icons.Default.Person,
            contentDescription = "Book cover",
            modifier = Modifier
                .size(70.dp)
                .background(Color.Gray)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .background(Color.LightGray)
                .padding(10.dp,5.dp),
        ) {
            Text(
                text = book.title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )

            Text(
                text = book.author,
                fontWeight = FontWeight.Light
            )

            Text(
                text = book.publisher,
                fontWeight = FontWeight.Light
            )
        }
    }
}