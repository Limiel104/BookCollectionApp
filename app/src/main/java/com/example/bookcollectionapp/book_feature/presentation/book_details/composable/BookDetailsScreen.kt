package com.example.bookcollectionapp.book_feature.presentation.book_details.composable

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookcollectionapp.R
import com.example.bookcollectionapp.book_feature.presentation.book_details.BookDetailsViewModel
import com.example.bookcollectionapp.book_feature.presentation.util.Screen
import java.text.SimpleDateFormat
import java.util.*

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
    val readingStatus = viewModel.bookDetails.value.readingStatus
    val rating = viewModel.bookDetails.value.rating
    val language = viewModel.bookDetails.value.language

    val simpleDate = SimpleDateFormat("yyyy-MM-dd")
    val dateAdded = simpleDate.format(Date(viewModel.bookDetails.value.dateAdded))

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditBookScreen.route + "?bookId=${bookId}")
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit book",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
        scaffoldState = scaffoldState
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
                .padding(horizontal = 15.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
            ) {
                Card(
                    modifier = Modifier
                        .size(120.dp, 160.dp),
                    border = BorderStroke(0.dp, MaterialTheme.colors.background)
                ){
                    Box(
                        contentAlignment = Alignment.Center
                    ){
                        AsyncImage(
                            model = ImageRequest
                                .Builder(LocalContext.current)
                                .data(imagePath)
                                .build(),
                            contentDescription = "Selected image",
                            contentScale = ContentScale.Crop,
                            fallback = painterResource(R.drawable.ic_camera),
                            error = painterResource(R.drawable.ic_camera),
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(top = 20.dp)
                ) {
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start,
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = author,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Light,
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Start,
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            DetailsListDateItem(
                date = dateAdded
            )

            Divider()

            DetailsListRatingItem(
                rating = rating
            )

            Divider()

            DetailsListItem(
                label = "Status",
                text = readingStatus
            )

            Divider()

            DetailsListItem(
                label = "Publisher",
                text = publisher
            )

            Divider()

            DetailsListItem(
                label = "Genre",
                text = genre
            )

            Divider()

            DetailsListItem(
                label = "Language",
                text = language
            )
        }
    }
}