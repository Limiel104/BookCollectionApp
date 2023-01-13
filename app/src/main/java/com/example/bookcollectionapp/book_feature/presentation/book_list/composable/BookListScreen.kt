package com.example.bookcollectionapp.book_feature.presentation.book_list.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bookcollectionapp.book_feature.presentation.book_list.BookListViewModel

@Composable
fun BookListScreen(
    navController: NavController,
    viewModel: BookListViewModel = hiltViewModel()
){
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add book")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp,0.dp)
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = { /*TODO*/},
                modifier = Modifier
                    .padding(5.dp,10.dp)
                    .fillMaxWidth(),
                placeholder = {
                              Text(text = "Search...")
                },
                maxLines = 1,
                singleLine = true
            )
            
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ){
                items(state.bookList) { book ->
                    BookListItem(
                        book = book,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}