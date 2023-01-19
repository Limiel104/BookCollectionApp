package com.example.bookcollectionapp.book_feature.presentation.book_list.composable

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bookcollectionapp.book_feature.domain.util.BookOrder

@Composable
fun SortSection(
    modifier: Modifier = Modifier,
    bookOrder: BookOrder = BookOrder.TitleAscending(),
    onOrderChange: (BookOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 10.dp
                ),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SortSectionItem(
                text = "Title A-Z",
                selected = bookOrder is BookOrder.TitleAscending,
                onClick = { onOrderChange(BookOrder.TitleAscending()) }
            )

            Spacer(modifier = Modifier.width(10.dp))

            SortSectionItem(
                text = "Most Recent",
                selected = bookOrder is BookOrder.DateDescending,
                onClick = { onOrderChange(BookOrder.DateDescending()) }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 10.dp
                ),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SortSectionItem(
                text = "Title Z-A",
                selected = bookOrder is BookOrder.TitleDescending,
                onClick = { onOrderChange(BookOrder.TitleDescending()) }
            )

            Spacer(modifier = Modifier.width(10.dp))

            SortSectionItem(
                text = "Least Recent",
                selected = bookOrder is BookOrder.DateAscending,
                onClick = { onOrderChange(BookOrder.DateAscending()) }
            )
        }
    }
}