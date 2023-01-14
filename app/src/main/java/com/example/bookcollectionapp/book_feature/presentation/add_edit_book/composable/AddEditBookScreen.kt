package com.example.bookcollectionapp.book_feature.presentation.add_edit_book.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bookcollectionapp.book_feature.presentation.add_edit_book.AddEditBookEvent
import com.example.bookcollectionapp.book_feature.presentation.add_edit_book.AddEditBookViewModel
import com.example.bookcollectionapp.book_feature.presentation.add_edit_book.UiEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddEditBookScreen(
    navController: NavController,
    viewModel: AddEditBookViewModel = hiltViewModel()
) {

    val titleState = viewModel.bookTitle.value
    val authorState = viewModel.bookAuthor.value
    val publisherState = viewModel.bookPublisher.value

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is UiEvent.SaveBook -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                viewModel.onEvent(AddEditBookEvent.SaveBook)
                },
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add book")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            OutlinedTextField(
                value = titleState.text,
                label = { Text("Title") },
                onValueChange = { viewModel.onEvent(AddEditBookEvent.EnteredTitle(it)) },
                placeholder = { Text(titleState.hint) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
//                modifier = Modifier.hint
//                    .onFocusChanged {
//                    viewModel.onEvent(AddEditBookEvent.ChangeTitleFocus(it))
//                }
            )

            OutlinedTextField(
                value = authorState.text,
                label = { Text("Author") },
                onValueChange = { viewModel.onEvent(AddEditBookEvent.EnteredAuthor(it)) },
                placeholder = { Text(authorState.hint) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = publisherState.text,
                label = { Text("Publisher") },
                onValueChange = { viewModel.onEvent(AddEditBookEvent.EnteredPublisher(it)) },
                placeholder = { Text(publisherState.hint) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}