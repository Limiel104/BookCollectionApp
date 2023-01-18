package com.example.bookcollectionapp.book_feature.presentation.book_list.composable

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.bookcollectionapp.R
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
        Log.i("TAGBookList",book.imagePath)

        AsyncImage(
            model = book.imagePath,
            contentDescription = "Book cover",
            contentScale = ContentScale.Crop,
            fallback = painterResource(R.drawable.ic_camera),
            modifier = Modifier
                .size(70.dp, 100.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .background(Color.LightGray)
                .padding(10.dp, 5.dp),
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