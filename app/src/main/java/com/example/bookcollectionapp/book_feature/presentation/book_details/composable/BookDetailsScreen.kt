package com.example.bookcollectionapp.book_feature.presentation.book_details.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.bookcollectionapp.ui.theme.*

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
    val starsNum = 5

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        backgroundColor = BlueDark,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditBookScreen.route + "?bookId=${bookId}")
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit book",
                    tint = Color.White
                )
            }
        },
        scaffoldState = scaffoldState
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .background(BlueDark)
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                AsyncImage(
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(imagePath)
                        .build(),
                    contentDescription = "Selected image",
                    contentScale = ContentScale.Crop,
                    fallback = painterResource(R.drawable.ic_camera),
                    error = painterResource(R.drawable.ic_camera),
                    modifier = Modifier
                        .size(160.dp, 210.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                )

                Text(
                    text = author,
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(bottom = 15.dp)
                )
            }

            Card(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                shape = RoundedCornerShape(30.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.height(25.dp))

                        Text(
                            text = "Date Added",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light,
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.Center,
                        )

                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Date",
                                tint = Red
                            )

                            Text(
                                text = "2019-03-09",
                            )
                        }

                        Spacer(modifier = Modifier.height(25.dp))

                        Text(
                            text = "Publisher",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light,
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.Center,
                        )

                        Text(
                            text = publisher,
                            textAlign = TextAlign.Center,
                        )

                        Spacer(modifier = Modifier.height(25.dp))

                        Text(
                            text = "Status",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light,
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.Center,
                        )

                        Text(
                            text = "Completed",
                            textAlign = TextAlign.Center,
                        )
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.height(25.dp))

                        Text(
                            text = "Rating",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light,
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.Center,
                        )

                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            for (star in 1..starsNum) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Rating",
                                    tint = Yellow
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(25.dp))

                        Text(
                            text = "Genre",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light,
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.Center,
                        )

                        Text(
                            text = genre,
                            textAlign = TextAlign.Center,
                        )

                        Spacer(modifier = Modifier.height(25.dp))

                        Text(
                            text = "Language",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light,
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.Center,
                        )

                        Text(
                            text = "Polish",
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}