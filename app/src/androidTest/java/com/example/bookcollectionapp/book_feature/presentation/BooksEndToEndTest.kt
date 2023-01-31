package com.example.bookcollectionapp.book_feature.presentation

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bookcollectionapp.book_feature.domain.util.Genre
import com.example.bookcollectionapp.book_feature.domain.util.Language
import com.example.bookcollectionapp.book_feature.domain.util.Rating
import com.example.bookcollectionapp.book_feature.domain.util.ReadingStatus
import com.example.bookcollectionapp.book_feature.presentation.add_edit_book.composable.AddEditBookScreen
import com.example.bookcollectionapp.book_feature.presentation.book_details.composable.BookDetailsScreen
import com.example.bookcollectionapp.book_feature.presentation.book_list.composable.BookListScreen
import com.example.bookcollectionapp.book_feature.presentation.util.Screen
import com.example.bookcollectionapp.common.TestTags
import com.example.bookcollectionapp.di.AppModule
import com.example.bookcollectionapp.ui.theme.BookCollectionAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class BooksEndToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            BookCollectionAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.BookListScreen.route
                ) {
                    composable(
                        route = Screen.BookListScreen.route
                    ) {
                        BookListScreen(navController = navController)
                    }
                    composable(
                        route = Screen.AddEditBookScreen.route + "?bookId={bookId}",
                        arguments = listOf(
                            navArgument(
                                name = "bookId"
                            ) {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        AddEditBookScreen(navController = navController)
                    }
                    composable(
                        route = Screen.BookDetailsScreen.route + "bookId={bookId}",
                        arguments = listOf(
                            navArgument(
                                name = "bookId"
                            ) {
                                type = NavType.IntType
                            }
                        )
                    ) {
                        BookDetailsScreen(navController = navController)
                    }
                }
            }
        }
    }

    @Test
    fun book_addNewBook_bookIsOnTheBookList() {
        composeRule.onNodeWithContentDescription("Add book").performClick()

        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput("Book title")
        composeRule.onNodeWithTag(TestTags.AUTHOR_TEXT_FIELD).performTextInput("Book author")

        composeRule.onNodeWithText(Genre.Adventure.value).assertDoesNotExist()
        composeRule.onNodeWithTag(TestTags.GENRE_TEXT_FIELD).performClick()
        composeRule.onNodeWithText(Genre.Adventure.value).assertExists()
        composeRule.onNodeWithText(Genre.Adventure.value).performClick()

        composeRule.onNodeWithText(Language.English.value).assertDoesNotExist()
        composeRule.onNodeWithTag(TestTags.LANGUAGE_TEXT_FIELD).performClick()
        composeRule.onNodeWithText(Language.English.value).assertExists()
        composeRule.onNodeWithText(Language.English.value).performClick()

        composeRule.onNodeWithText(ReadingStatus.Reading.value).assertDoesNotExist()
        composeRule.onNodeWithTag(TestTags.READING_STATUS_TEXT_FIELD).performClick()
        composeRule.onNodeWithText(ReadingStatus.Reading.value).assertExists()
        composeRule.onNodeWithText(ReadingStatus.Reading.value).performClick()

        composeRule.onNodeWithText(Rating.Five.value).assertDoesNotExist()
        composeRule.onNodeWithTag(TestTags.RATING_TEXT_FIELD).performClick()
        composeRule.onNodeWithText(Rating.Five.value).assertExists()
        composeRule.onNodeWithText(Rating.Five.value).performClick()

        composeRule.onNodeWithTag(TestTags.PUBLISHER_TEXT_FIELD).performTextInput("Book publisher")

        composeRule.onNodeWithContentDescription("Save book").performClick()
        composeRule.onNodeWithText("Book title").assertIsDisplayed()
    }

    @Test
    fun bookList_checkIfBooksAreSortedInDefaultOrder_orderIsCorrect() {
        for(i in 1..3) {
            composeRule.onNodeWithContentDescription("Add book").performClick()
            composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput("Book title $i")
            composeRule.onNodeWithTag(TestTags.AUTHOR_TEXT_FIELD).performTextInput("Book author")
            composeRule.onNodeWithTag(TestTags.GENRE_TEXT_FIELD).performClick()
            composeRule.onNodeWithText(Genre.Adventure.value).performClick()
            composeRule.onNodeWithTag(TestTags.LANGUAGE_TEXT_FIELD).performClick()
            composeRule.onNodeWithText(Language.English.value).performClick()
            composeRule.onNodeWithTag(TestTags.READING_STATUS_TEXT_FIELD).performClick()
            composeRule.onNodeWithText(ReadingStatus.Reading.value).performClick()
            composeRule.onNodeWithTag(TestTags.RATING_TEXT_FIELD).performClick()
            composeRule.onNodeWithText(Rating.Five.value).performClick()
            composeRule.onNodeWithTag(TestTags.PUBLISHER_TEXT_FIELD).performTextInput("Book publisher")
            composeRule.onNodeWithContentDescription("Save book").performClick()
        }

        composeRule.onNodeWithText("Book title 1").assertIsDisplayed()
        composeRule.onNodeWithText("Book title 2").assertIsDisplayed()
        composeRule.onNodeWithText("Book title 3").assertIsDisplayed()

        composeRule.onAllNodesWithTag(TestTags.BOOK_ITEM)[0].assertTextContains("Book title 1")
        composeRule.onAllNodesWithTag(TestTags.BOOK_ITEM)[1].assertTextContains("Book title 2")
        composeRule.onAllNodesWithTag(TestTags.BOOK_ITEM)[2].assertTextContains("Book title 3")
    }

    @Test
    fun bookList_setOrderToTitleDescending_orderIsCorrect() {
        for(i in 1..3) {
            composeRule.onNodeWithContentDescription("Add book").performClick()
            composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput("Book title $i")
            composeRule.onNodeWithTag(TestTags.AUTHOR_TEXT_FIELD).performTextInput("Book author")
            composeRule.onNodeWithTag(TestTags.GENRE_TEXT_FIELD).performClick()
            composeRule.onNodeWithText(Genre.Adventure.value).performClick()
            composeRule.onNodeWithTag(TestTags.LANGUAGE_TEXT_FIELD).performClick()
            composeRule.onNodeWithText(Language.English.value).performClick()
            composeRule.onNodeWithTag(TestTags.READING_STATUS_TEXT_FIELD).performClick()
            composeRule.onNodeWithText(ReadingStatus.Reading.value).performClick()
            composeRule.onNodeWithTag(TestTags.RATING_TEXT_FIELD).performClick()
            composeRule.onNodeWithText(Rating.Five.value).performClick()
            composeRule.onNodeWithTag(TestTags.PUBLISHER_TEXT_FIELD).performTextInput("Book publisher")
            composeRule.onNodeWithContentDescription("Save book").performClick()
        }

        composeRule.onNodeWithText("Book title 1").assertIsDisplayed()
        composeRule.onNodeWithText("Book title 2").assertIsDisplayed()
        composeRule.onNodeWithText("Book title 3").assertIsDisplayed()

        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_TYPE_TITLE_DESC).performClick()
        composeRule.onNodeWithContentDescription("Sort").performClick()

        composeRule.onAllNodesWithTag(TestTags.BOOK_ITEM)[0].assertTextContains("Book title 3")
        composeRule.onAllNodesWithTag(TestTags.BOOK_ITEM)[1].assertTextContains("Book title 2")
        composeRule.onAllNodesWithTag(TestTags.BOOK_ITEM)[2].assertTextContains("Book title 1")
    }

    @Test
    fun bookList_setOrderToDateAscending_orderIsCorrect() {
        for(i in listOf("One","Two","Three")) {
            composeRule.onNodeWithContentDescription("Add book").performClick()
            composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput("Book title $i")
            composeRule.onNodeWithTag(TestTags.AUTHOR_TEXT_FIELD).performTextInput("Book author")
            composeRule.onNodeWithTag(TestTags.GENRE_TEXT_FIELD).performClick()
            composeRule.onNodeWithText(Genre.Adventure.value).performClick()
            composeRule.onNodeWithTag(TestTags.LANGUAGE_TEXT_FIELD).performClick()
            composeRule.onNodeWithText(Language.English.value).performClick()
            composeRule.onNodeWithTag(TestTags.READING_STATUS_TEXT_FIELD).performClick()
            composeRule.onNodeWithText(ReadingStatus.Reading.value).performClick()
            composeRule.onNodeWithTag(TestTags.RATING_TEXT_FIELD).performClick()
            composeRule.onNodeWithText(Rating.Five.value).performClick()
            composeRule.onNodeWithTag(TestTags.PUBLISHER_TEXT_FIELD).performTextInput("Book publisher")
            composeRule.onNodeWithContentDescription("Save book").performClick()
        }

        composeRule.onNodeWithText("Book title One").assertIsDisplayed()
        composeRule.onNodeWithText("Book title Three").assertIsDisplayed()
        composeRule.onNodeWithText("Book title Two").assertIsDisplayed()

        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_TYPE_DATE_ASC).performClick()
        composeRule.onNodeWithContentDescription("Sort").performClick()

        composeRule.onAllNodesWithTag(TestTags.BOOK_ITEM)[0].assertTextContains("Book title One")
        composeRule.onAllNodesWithTag(TestTags.BOOK_ITEM)[1].assertTextContains("Book title Two")
        composeRule.onAllNodesWithTag(TestTags.BOOK_ITEM)[2].assertTextContains("Book title Three")
    }

    @Test
    fun bookList_setOrderToDateDescending_orderIsCorrect() {
        for(i in listOf("One","Two","Three")) {
            composeRule.onNodeWithContentDescription("Add book").performClick()
            composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput("Book title $i")
            composeRule.onNodeWithTag(TestTags.AUTHOR_TEXT_FIELD).performTextInput("Book author")
            composeRule.onNodeWithTag(TestTags.GENRE_TEXT_FIELD).performClick()
            composeRule.onNodeWithText(Genre.Adventure.value).performClick()
            composeRule.onNodeWithTag(TestTags.LANGUAGE_TEXT_FIELD).performClick()
            composeRule.onNodeWithText(Language.English.value).performClick()
            composeRule.onNodeWithTag(TestTags.READING_STATUS_TEXT_FIELD).performClick()
            composeRule.onNodeWithText(ReadingStatus.Reading.value).performClick()
            composeRule.onNodeWithTag(TestTags.RATING_TEXT_FIELD).performClick()
            composeRule.onNodeWithText(Rating.Five.value).performClick()
            composeRule.onNodeWithTag(TestTags.PUBLISHER_TEXT_FIELD).performTextInput("Book publisher")
            composeRule.onNodeWithContentDescription("Save book").performClick()
        }

        composeRule.onNodeWithText("Book title One").assertIsDisplayed()
        composeRule.onNodeWithText("Book title Three").assertIsDisplayed()
        composeRule.onNodeWithText("Book title Two").assertIsDisplayed()

        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_TYPE_DATE_DESC).performClick()
        composeRule.onNodeWithContentDescription("Sort").performClick()

        composeRule.onAllNodesWithTag(TestTags.BOOK_ITEM)[0].assertTextContains("Book title Three")
        composeRule.onAllNodesWithTag(TestTags.BOOK_ITEM)[1].assertTextContains("Book title Two")
        composeRule.onAllNodesWithTag(TestTags.BOOK_ITEM)[2].assertTextContains("Book title One")
    }

    @Test
    fun bookListItem_clickBookOnTheList_navigateToDetailsScreen() {
        composeRule.onNodeWithContentDescription("Add book").performClick()
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput("Book title")
        composeRule.onNodeWithTag(TestTags.AUTHOR_TEXT_FIELD).performTextInput("Book author")
        composeRule.onNodeWithTag(TestTags.GENRE_TEXT_FIELD).performClick()
        composeRule.onNodeWithText(Genre.Adventure.value).performClick()
        composeRule.onNodeWithTag(TestTags.LANGUAGE_TEXT_FIELD).performClick()
        composeRule.onNodeWithText(Language.English.value).performClick()
        composeRule.onNodeWithTag(TestTags.READING_STATUS_TEXT_FIELD).performClick()
        composeRule.onNodeWithText(ReadingStatus.Reading.value).performClick()
        composeRule.onNodeWithTag(TestTags.RATING_TEXT_FIELD).performClick()
        composeRule.onNodeWithText(Rating.Five.value).performClick()
        composeRule.onNodeWithTag(TestTags.PUBLISHER_TEXT_FIELD).performTextInput("Book publisher")
        composeRule.onNodeWithContentDescription("Save book").performClick()

        composeRule.onNodeWithTag(TestTags.BOOK_ITEM).performClick()
        composeRule.onNodeWithContentDescription("Edit book").assertExists()
    }

    @Test
    fun book_clickBokOnTheList_bookDetailAreCorrect() {
        composeRule.onNodeWithContentDescription("Add book").performClick()
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput("Book title")
        composeRule.onNodeWithTag(TestTags.AUTHOR_TEXT_FIELD).performTextInput("Book author")
        composeRule.onNodeWithTag(TestTags.GENRE_TEXT_FIELD).performClick()
        composeRule.onNodeWithText(Genre.Adventure.value).performClick()
        composeRule.onNodeWithTag(TestTags.LANGUAGE_TEXT_FIELD).performClick()
        composeRule.onNodeWithText(Language.English.value).performClick()
        composeRule.onNodeWithTag(TestTags.READING_STATUS_TEXT_FIELD).performClick()
        composeRule.onNodeWithText(ReadingStatus.Reading.value).performClick()
        composeRule.onNodeWithTag(TestTags.RATING_TEXT_FIELD).performClick()
        composeRule.onNodeWithText(Rating.Five.value).performClick()
        composeRule.onNodeWithTag(TestTags.PUBLISHER_TEXT_FIELD).performTextInput("Book publisher")
        composeRule.onNodeWithContentDescription("Save book").performClick()

        composeRule.onNodeWithTag(TestTags.BOOK_ITEM).performClick()

        composeRule.onNodeWithTag(TestTags.BOOK_DETAILS_TITLE).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.BOOK_DETAILS_TITLE).assertTextContains("Book title")

        composeRule.onNodeWithTag(TestTags.BOOK_DETAILS_AUTHOR).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.BOOK_DETAILS_AUTHOR).assertTextContains("Book author")

        composeRule.onAllNodesWithTag(TestTags.BOOK_DETAILS_RATING).assertCountEquals(5)

        composeRule.onNodeWithContentDescription("Status").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Status").assertTextContains("Reading")

        composeRule.onNodeWithContentDescription("Publisher").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Publisher").assertTextContains("Book publisher")

        composeRule.onNodeWithContentDescription("Genre").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Genre").assertTextContains("Adventure")

        composeRule.onNodeWithContentDescription("Language").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Language").assertTextContains("English")
    }
}