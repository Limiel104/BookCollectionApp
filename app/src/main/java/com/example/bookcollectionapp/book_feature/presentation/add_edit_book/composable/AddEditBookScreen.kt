package com.example.bookcollectionapp.book_feature.presentation.add_edit_book.composable

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookcollectionapp.R
import com.example.bookcollectionapp.book_feature.domain.util.getAllGenresAsStrings
import com.example.bookcollectionapp.book_feature.domain.util.getAllLanguages
import com.example.bookcollectionapp.book_feature.domain.util.getAllRatings
import com.example.bookcollectionapp.book_feature.domain.util.getAllReadingStatuses
import com.example.bookcollectionapp.book_feature.presentation.add_edit_book.AddEditBookEvent
import com.example.bookcollectionapp.book_feature.presentation.add_edit_book.AddEditBookViewModel
import com.example.bookcollectionapp.book_feature.presentation.add_edit_book.UiEvent
import com.example.bookcollectionapp.book_feature.presentation.util.Screen
import com.example.bookcollectionapp.common.TestTags
import com.example.bookcollectionapp.common.write
import kotlinx.coroutines.flow.collectLatest
import java.io.File
import java.util.UUID

@Composable
fun AddEditBookScreen(
    navController: NavController,
    viewModel: AddEditBookViewModel = hiltViewModel()
) {

    val titleState = viewModel.bookTextFieldsState.value.title
    val authorState = viewModel.bookTextFieldsState.value.author
    val publisherState = viewModel.bookTextFieldsState.value.publisher
    val genreState = viewModel.bookGenre.value
    val imagePathState = viewModel.bookImagePath.value
    val languageState = viewModel.bookLanguage.value
    val readingStatusState = viewModel.bookReadingStatus.value
    val ratingState = viewModel.bookRating.value
    val errorState = viewModel.bookValidationErrorsState.value

    val scrollState = rememberScrollState()

    val context = LocalContext.current
    val dirPath = context.filesDir.path
    var fileName = imagePathState.imageFileName

    if (fileName == "") {
        fileName = "/" + UUID.randomUUID().toString() + ".jpeg"
        viewModel.onEvent(AddEditBookEvent.PickedNewFileName(fileName))
    }

    val filePath = dirPath + fileName

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (Build.VERSION.SDK_INT < 29) {
                val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                File(dirPath, fileName).write(bitmap, Bitmap.CompressFormat.JPEG, 40)
                viewModel.onEvent(AddEditBookEvent.PickedImage(filePath))
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, uri!!)
                val bitmap = ImageDecoder.decodeBitmap(source)
                File(dirPath, fileName).write(bitmap, Bitmap.CompressFormat.JPEG, 40)
                viewModel.onEvent(AddEditBookEvent.PickedImage(filePath))
            }
        }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            File(dirPath, fileName).write(bitmap!!, Bitmap.CompressFormat.JPEG, 40)
            viewModel.onEvent(AddEditBookEvent.PickedImage(filePath))
        }

    val iconGenre = if (genreState.isExpanded) {
        Icons.Default.KeyboardArrowUp
    } else {
        Icons.Default.KeyboardArrowDown
    }

    val iconLanguage = if (languageState.isExpanded) {
        Icons.Default.KeyboardArrowUp
    } else {
        Icons.Default.KeyboardArrowDown
    }

    val iconReadingStatus = if (readingStatusState.isExpanded) {
        Icons.Default.KeyboardArrowUp
    } else {
        Icons.Default.KeyboardArrowDown
    }

    val iconRating = if (ratingState.isExpanded) {
        Icons.Default.KeyboardArrowUp
    } else {
        Icons.Default.KeyboardArrowDown
    }

    val genreList = getAllGenresAsStrings()
    val languageList = getAllLanguages()
    val readingStatusList = getAllReadingStatuses()
    val ratingList = getAllRatings()

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is UiEvent.SaveBook -> {
                    navController.popBackStack(
                        Screen.BookListScreen.route,
                        inclusive = false,
                        saveState = false
                    )
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
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Save book",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
        scaffoldState = scaffoldState
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    state = scrollState
                )
                .padding(10.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .size(150.dp, 200.dp)
                        .clickable { galleryLauncher.launch("image/*") }
                        .padding(5.dp),
                    border = BorderStroke(0.dp, MaterialTheme.colors.background)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        if (imagePathState.isCoverChanged) {
                            AsyncImage(
                                model = ImageRequest
                                    .Builder(LocalContext.current)
                                    .data(filePath)
                                    .build(),
                                contentDescription = "Selected image",
                                contentScale = ContentScale.Crop,
                                fallback = painterResource(R.drawable.ic_camera),
                                error = painterResource(R.drawable.ic_camera),
                            )
                        } else {
                            AsyncImage(
                                model = ImageRequest
                                    .Builder(LocalContext.current)
                                    .data(imagePathState.imagePath)
                                    .build(),
                                contentDescription = "Selected image",
                                contentScale = ContentScale.Crop,
                                fallback = painterResource(R.drawable.ic_camera),
                                error = painterResource(R.drawable.ic_camera),
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    OutlinedButton(
                        colors = ButtonDefaults.outlinedButtonColors(MaterialTheme.colors.primaryVariant),
                        onClick = {
                            galleryLauncher.launch("image/*")
                        }
                    ) {
                        Text(
                            text = "Choose Image",
                            color = MaterialTheme.colors.onPrimary
                        )
                    }

                    OutlinedButton(
                        colors = ButtonDefaults.outlinedButtonColors(MaterialTheme.colors.primaryVariant),
                        onClick = {
                            cameraLauncher.launch()
                        }
                    ) {
                        Text(
                            text = "Take a picture",
                            color = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            }

            Column {
                OutlinedTextField(
                    value = titleState,
                    label = { Text("Title") },
                    onValueChange = { viewModel.onEvent(AddEditBookEvent.EnteredTitle(it)) },
                    placeholder = { Text("Title") },
                    singleLine = true,
                    isError = errorState.titleError != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(TestTags.TITLE_TEXT_FIELD)
                )

                if (errorState.titleError != null) {
                    ErrorTextFieldItem(
                        errorMessage = errorState.titleError
                    )
                }

                OutlinedTextField(
                    value = authorState,
                    label = { Text("Author") },
                    onValueChange = { viewModel.onEvent(AddEditBookEvent.EnteredAuthor(it)) },
                    placeholder = { Text("Author") },
                    singleLine = true,
                    isError = errorState.authorError != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(TestTags.AUTHOR_TEXT_FIELD)
                )

                if (errorState.authorError != null) {
                    ErrorTextFieldItem(
                        errorMessage = errorState.authorError
                    )
                }

                Row(
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        OutlinedTextField(
                            value = genreState.selectedOption,
                            onValueChange = { viewModel.onEvent(AddEditBookEvent.ChosenGenre(it)) },
                            label = {
                                Text(text = "Genre")
                            },
                            maxLines = 1,
                            trailingIcon = {
                                Icon(
                                    imageVector = iconGenre,
                                    contentDescription = "Genre",
                                    modifier = Modifier
                                        .testTag(TestTags.GENRE_TEXT_FIELD)
                                        .clickable {
                                            viewModel.onEvent(
                                                AddEditBookEvent.GenreDropdownMenuStateChanged(
                                                    !genreState.isExpanded
                                                )
                                            )
                                        }
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    viewModel.onEvent(
                                        AddEditBookEvent.SizeOfGenreTextFieldChanged(
                                            coordinates.size.toSize()
                                        )
                                    )
                                },
                            readOnly = true
                        )

                        DropdownMenu(
                            expanded = genreState.isExpanded,
                            onDismissRequest = {
                                viewModel.onEvent(
                                    AddEditBookEvent.GenreDropdownMenuStateChanged(
                                        false
                                    )
                                )
                            },
                            modifier = Modifier
                                .width(with(LocalDensity.current) {
                                    genreState.textFieldSize.width.toDp()
                                })
                        ) {
                            genreList.forEach { option ->
                                DropdownMenuItem(
                                    onClick = {
                                        viewModel.onEvent(AddEditBookEvent.ChosenGenre(option))
                                        viewModel.onEvent(
                                            AddEditBookEvent.GenreDropdownMenuStateChanged(
                                                false
                                            )
                                        )
                                    }
                                ) {
                                    Text(text = option)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        OutlinedTextField(
                            value = languageState.selectedOption,
                            onValueChange = { viewModel.onEvent(AddEditBookEvent.ChosenLanguage(it)) },
                            label = {
                                Text(text = "Language")
                            },
                            maxLines = 1,
                            trailingIcon = {
                                Icon(
                                    imageVector = iconLanguage,
                                    contentDescription = "Language",
                                    modifier = Modifier
                                        .testTag(TestTags.LANGUAGE_TEXT_FIELD)
                                        .clickable {
                                            viewModel.onEvent(
                                                AddEditBookEvent.LanguageDropdownMenuStateChanged(
                                                    !languageState.isExpanded
                                                )
                                            )
                                        }
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    viewModel.onEvent(
                                        AddEditBookEvent.SizeOfLanguageTextFieldChanged(
                                            coordinates.size.toSize()
                                        )
                                    )
                                },
                            readOnly = true
                        )

                        DropdownMenu(
                            expanded = languageState.isExpanded,
                            onDismissRequest = {
                                viewModel.onEvent(
                                    AddEditBookEvent.LanguageDropdownMenuStateChanged(
                                        false
                                    )
                                )
                            },
                            modifier = Modifier
                                .width(with(LocalDensity.current) {
                                    languageState.textFieldSize.width.toDp()
                                })
                        ) {
                            languageList.forEach { option ->
                                DropdownMenuItem(
                                    onClick = {
                                        viewModel.onEvent(AddEditBookEvent.ChosenLanguage(option))
                                        viewModel.onEvent(
                                            AddEditBookEvent.LanguageDropdownMenuStateChanged(
                                                false
                                            )
                                        )
                                    }
                                ) {
                                    Text(text = option)
                                }
                            }
                        }
                    }
                }

                if (errorState.genreError != null) {
                    ErrorTextFieldItem(
                        errorMessage = errorState.genreError
                    )
                }

                if (errorState.languageError != null) {
                    ErrorTextFieldItem(
                        errorMessage = errorState.languageError
                    )
                }

                Row(
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        OutlinedTextField(
                            value = readingStatusState.selectedOption,
                            onValueChange = {
                                viewModel.onEvent(
                                    AddEditBookEvent.ChosenReadingStatus(
                                        it
                                    )
                                )
                            },
                            label = {
                                Text(text = "Status")
                            },
                            maxLines = 1,
                            trailingIcon = {
                                Icon(
                                    imageVector = iconReadingStatus,
                                    contentDescription = "Status",
                                    modifier = Modifier
                                        .testTag(TestTags.READING_STATUS_TEXT_FIELD)
                                        .clickable {
                                            viewModel.onEvent(
                                                AddEditBookEvent.ReadingStatusDropdownMenuStateChanged(
                                                    !readingStatusState.isExpanded
                                                )
                                            )
                                        }
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    viewModel.onEvent(
                                        AddEditBookEvent.SizeOfReadingStatusTextFieldChanged(
                                            coordinates.size.toSize()
                                        )
                                    )
                                },
                            readOnly = true
                        )

                        DropdownMenu(
                            expanded = readingStatusState.isExpanded,
                            onDismissRequest = {
                                viewModel.onEvent(
                                    AddEditBookEvent.ReadingStatusDropdownMenuStateChanged(
                                        false
                                    )
                                )
                            },
                            modifier = Modifier
                                .width(with(LocalDensity.current) {
                                    readingStatusState.textFieldSize.width.toDp()
                                })
                        ) {
                            readingStatusList.forEach { option ->
                                DropdownMenuItem(
                                    onClick = {
                                        viewModel.onEvent(
                                            AddEditBookEvent.ChosenReadingStatus(
                                                option
                                            )
                                        )
                                        viewModel.onEvent(
                                            AddEditBookEvent.ReadingStatusDropdownMenuStateChanged(
                                                false
                                            )
                                        )
                                    }
                                ) {
                                    Text(text = option)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        OutlinedTextField(
                            value = ratingState.selectedOption,
                            onValueChange = { viewModel.onEvent(AddEditBookEvent.ChosenRating(it)) },
                            label = {
                                Text(text = "Rating")
                            },
                            maxLines = 1,
                            trailingIcon = {
                                Icon(
                                    imageVector = iconRating,
                                    contentDescription = "Rating",
                                    modifier = Modifier
                                        .testTag(TestTags.RATING_TEXT_FIELD)
                                        .clickable {
                                            viewModel.onEvent(
                                                AddEditBookEvent.RatingDropdownMenuStateChanged(
                                                    !ratingState.isExpanded
                                                )
                                            )
                                        }
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    viewModel.onEvent(
                                        AddEditBookEvent.SizeOfRatingTextFieldChanged(
                                            coordinates.size.toSize()
                                        )
                                    )
                                },
                            readOnly = true
                        )

                        DropdownMenu(
                            expanded = ratingState.isExpanded,
                            onDismissRequest = {
                                viewModel.onEvent(
                                    AddEditBookEvent.RatingDropdownMenuStateChanged(
                                        false
                                    )
                                )
                            },
                            modifier = Modifier
                                .width(with(LocalDensity.current) {
                                    ratingState.textFieldSize.width.toDp()
                                })
                        ) {
                            ratingList.forEach { option ->
                                DropdownMenuItem(
                                    onClick = {
                                        viewModel.onEvent(AddEditBookEvent.ChosenRating(option))
                                        viewModel.onEvent(
                                            AddEditBookEvent.RatingDropdownMenuStateChanged(
                                                false
                                            )
                                        )
                                    }
                                ) {
                                    Text(text = option)
                                }
                            }
                        }
                    }
                }

                if (errorState.readingStatusError != null) {
                    ErrorTextFieldItem(
                        errorMessage = errorState.readingStatusError
                    )
                }

                if (errorState.ratingError != null) {
                    ErrorTextFieldItem(
                        errorMessage = errorState.ratingError
                    )
                }

                OutlinedTextField(
                    value = publisherState,
                    label = { Text("Publisher") },
                    onValueChange = { viewModel.onEvent(AddEditBookEvent.EnteredPublisher(it)) },
                    placeholder = { Text("Publisher") },
                    singleLine = true,
                    isError = errorState.publisherError != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(TestTags.PUBLISHER_TEXT_FIELD)
                )

                if (errorState.publisherError != null) {
                    ErrorTextFieldItem(
                        errorMessage = errorState.publisherError
                    )
                }
            }
        }
    }
}