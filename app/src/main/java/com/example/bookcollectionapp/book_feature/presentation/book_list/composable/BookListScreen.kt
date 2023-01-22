package com.example.bookcollectionapp.book_feature.presentation.book_list.composable

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bookcollectionapp.R
import com.example.bookcollectionapp.book_feature.domain.util.getAllGenres
import com.example.bookcollectionapp.book_feature.presentation.book_list.BookListEvent
import com.example.bookcollectionapp.book_feature.presentation.book_list.BookListViewModel
import com.example.bookcollectionapp.book_feature.presentation.util.Screen
import com.example.bookcollectionapp.ui.theme.Pink
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
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
                    tint = Color.White,
                    contentDescription = "Add book")
            }
        },
        scaffoldState = scaffoldState
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background)
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = state.searchQuery,
                    textStyle = TextStyle(fontSize = 13.sp),
                    onValueChange = { query ->
                        viewModel.onEvent(BookListEvent.OnSearchQueryChange(query))
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .background(MaterialTheme.colors.background),
                    placeholder = {
                        Text(
                            text = "Search title, author or publisher...",
                            fontSize = 13.sp
                        )
                    },
                    maxLines = 1,
                    singleLine = true,
                    shape = RoundedCornerShape(50.dp),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                viewModel.onEvent(BookListEvent.ToggleSortSection)
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_sort),
                                contentDescription = "cod")
                        }
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    },
                )
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
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

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
                modifier = Modifier
                    .fillMaxSize()
            ){
                itemsIndexed(
                    items = state.bookList,
                    key = { index, item ->
                        item.hashCode()
                    }
                ) { index, book ->
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if(it == DismissValue.DismissedToStart) {
                                viewModel.onEvent(BookListEvent.DeleteBook(book))
                                scope.launch {
                                    val result = scaffoldState.snackbarHostState.showSnackbar(
                                        message = "Book deleted",
                                        actionLabel = "Undo"
                                    )
                                    if(result == SnackbarResult.ActionPerformed) {
                                        viewModel.onEvent(BookListEvent.RestoreBook)
                                    }
                                }
                            }
                            true
                        }
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        background = {
                            val color = when(dismissState.dismissDirection) {
                                DismissDirection.StartToEnd -> Color.Transparent
                                DismissDirection.EndToStart -> Pink
                                null -> Color.Transparent
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(8.dp)
                            ){
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete book",
                                    tint = Color.White,
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                )
                            }
                        },
                        dismissContent = {
                            BookListItem(
                                book = book,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        navController.navigate(Screen.BookDetailsScreen.route + "bookId=${book.id}")
                                    }
                            )
                        },
                        directions = setOf(DismissDirection.EndToStart)
                    )
                    Divider()
                }
            }
        }
    }
}