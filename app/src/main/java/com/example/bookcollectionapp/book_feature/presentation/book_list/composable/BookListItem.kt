package com.example.bookcollectionapp.book_feature.presentation.book_list.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.bookcollectionapp.R
import com.example.bookcollectionapp.book_feature.domain.model.Book
import com.example.bookcollectionapp.ui.theme.Red
import com.example.bookcollectionapp.ui.theme.Yellow
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun BookListItem(
    book: Book,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(start = 5.dp)
            .padding(vertical = 7.dp)
            .background(MaterialTheme.colors.background),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val simpleDate = SimpleDateFormat("yyyy-MM-dd")

        AsyncImage(
            model = book.imagePath,
            contentDescription = "Book cover",
            contentScale = ContentScale.Crop,
            fallback = painterResource(R.drawable.ic_camera),
            modifier = Modifier
                .size(90.dp, 120.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 20.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = book.title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.padding(start = 5.dp)
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = book.author,
                fontWeight = FontWeight.Light,
                fontSize = 15.sp,
                modifier = Modifier.padding(start = 5.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))
            
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Rating",
                    tint = Yellow
                )

                Text(
                    text = book.rating.toString(),
                    fontWeight = FontWeight.Light,
                    fontSize = 13.sp,
                )

                Spacer(modifier = Modifier.width(20.dp))

                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Date",
                    tint = Red
                )

                Text(
                    text = simpleDate.format(Date(book.dateAdded)),
                    fontWeight = FontWeight.Light,
                    fontSize = 13.sp,
                )
            }
        }
    }
}