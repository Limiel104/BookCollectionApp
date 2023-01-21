package com.example.bookcollectionapp.book_feature.presentation.book_details.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
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
import com.example.bookcollectionapp.book_feature.presentation.book_details.BookDetailsViewModel
import com.example.bookcollectionapp.book_feature.presentation.util.Screen

@Composable
fun BookDetailsScreen(
    navController: NavController,
    viewModel: BookDetailsViewModel = hiltViewModel()
) {
    val bookId = viewModel.bookDetails.value.id
    val title = viewModel.bookDetails.value.title
    val author = viewModel.bookDetails.value.author
    val publisher = viewModel.bookDetails.value.publisher
    val genre = viewModel.bookDetails.value.genre
    val imagePath = viewModel.bookDetails.value.imagePath

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditBookScreen.route + "?bookId=${bookId}")
                },
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit book")
            }
        },
        scaffoldState = scaffoldState
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imagePath)
                    .build(),
                contentDescription = "Selected image",
                contentScale = ContentScale.Crop,
                fallback = painterResource(R.drawable.ic_camera),
                error = painterResource(R.drawable.ic_camera),
                modifier = Modifier
                    .size(170.dp, 250.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                Text(text = "Title: ")
                Text(text = title)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                Text(text = "Authir: ")
                Text(text = author)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                Text(text = "Publisher: ")
                Text(text = publisher)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                Text(text = "Genre: ")
                Text(text = genre)
            }
        }
    }
}