package com.example.bookcollectionapp.book_feature.presentation.add_edit_book.composable

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookcollectionapp.R
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
    val imagePathState = viewModel.bookImagePath.value

    var selectedImage by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        selectedImage = uri
        viewModel.onEvent(AddEditBookEvent.PickedImage(uri.toString()))
    }

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
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (imagePathState.imagePath == "") {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(selectedImage)
                        .build(),
                    contentDescription = "Selected image",
                    contentScale = ContentScale.Crop,
                    fallback = painterResource(R.drawable.ic_camera),
                    modifier = Modifier
                        .size(170.dp, 250.dp)
                        .clickable {
                            launcher.launch("image/*")
                        }
                )
            }
            else {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imagePathState.imagePath)
                        .build(),
                    contentDescription = "Selected image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(170.dp, 250.dp)
                        .clickable {
                            launcher.launch("image/*")
                        }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(
                    onClick = {
                        launcher.launch("image/*")
                    }) {
                    Text(text = "Choose Image")
                }

                OutlinedButton(
                    onClick = {
                        //launcher.launch("image/*")
                    }) {
                    Text(text = "Take a picture")
                }
            }


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