package com.example.bookcollectionapp.book_feature.presentation.book_list.composable

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bookcollectionapp.book_feature.domain.util.BookOrder

@Composable
fun SortSection(
    bookOrder: BookOrder = BookOrder.TitleAscending(),
    onOrderChange: (BookOrder) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .padding(top = 10.dp),
        ) {
            SortSectionItem(
                text = "Title A-Z",
                selected = bookOrder is BookOrder.TitleAscending,
                onClick = { onOrderChange(BookOrder.TitleAscending()) }
            )

            Spacer(modifier = Modifier.height(5.dp))

            SortSectionItem(
                text = "Title Z-A",
                selected = bookOrder is BookOrder.TitleDescending,
                onClick = { onOrderChange(BookOrder.TitleDescending()) }
            )
        }

        Column(
            modifier = Modifier
                .padding(top = 10.dp),
        ) {
            SortSectionItem(
                text = "Most Recent",
                selected = bookOrder is BookOrder.DateDescending,
                onClick = { onOrderChange(BookOrder.DateDescending()) }
            )

            Spacer(modifier = Modifier.height(5.dp))

            SortSectionItem(
                text = "Least Recent",
                selected = bookOrder is BookOrder.DateAscending,
                onClick = { onOrderChange(BookOrder.DateAscending()) }
            )
        }
    }
}