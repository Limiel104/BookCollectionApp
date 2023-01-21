package com.example.bookcollectionapp.book_feature.presentation.book_list.composable

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bookcollectionapp.R
import com.example.bookcollectionapp.book_feature.domain.util.getAllGenres
import com.example.bookcollectionapp.book_feature.presentation.book_list.BookListEvent
import com.example.bookcollectionapp.book_feature.presentation.book_list.BookListViewModel
import com.example.bookcollectionapp.book_feature.presentation.util.Screen

@Composable
fun BookListScreen(
    navController: NavController,
    viewModel: BookListViewModel = hiltViewModel()
){
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditBookScreen.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add book")
            }
        },
        scaffoldState = scaffoldState
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp, 0.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "My Books",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,

                )

                IconButton(
                    onClick = {
                        viewModel.onEvent(BookListEvent.ToggleSortSection)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_sort),
                        contentDescription = "Sort button"
                    )
                }
            }

            AnimatedVisibility(
                visible = state.isSortSectionExpanded,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                SortSection(
                    bookOrder = state.bookOrder,
                    onOrderChange = {
                        viewModel.onEvent(BookListEvent.Order(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 10.dp
                        )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = { query ->
                    viewModel.onEvent(BookListEvent.OnSearchQueryChange(query))
                },
                modifier = Modifier
                    .padding(5.dp, 10.dp)
                    .fillMaxWidth(),
                placeholder = {
                    Text(text = "Search title, author or publisher...")
                },
                maxLines = 1,
                singleLine = true
            )

            Row(
                modifier = Modifier
                    .horizontalScroll(
                        state = scrollState
                    )
            ) {
                for (genre in getAllGenres()) {
                    FilterSectionItem(
                        text = genre.value,
                        isSelected = state.filter == genre.value,
                        onClick = {
                            viewModel.onEvent(BookListEvent.OnFilterChange(it))
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ){
                items(state.bookList) { book ->
                    BookListItem(
                        book = book,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Screen.BookDetailsScreen.route + "bookId=${book.id}")
                            }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}