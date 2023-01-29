package com.example.bookcollectionapp.book_feature.presentation.book_list.composable

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bookcollectionapp.book_feature.domain.util.Genre
import com.example.bookcollectionapp.book_feature.presentation.MainActivity
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
class BookListScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            val navController = rememberNavController()
            BookCollectionAppTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.BookListScreen.route
                ) {
                    composable(
                        route = Screen.BookListScreen.route
                    ) {
                        BookListScreen(
                            navController = navController
                        )
                    }
                }
            }
        }
    }

    @Test
    fun searchField_clicked_isFocused() {
        composeRule.onNodeWithTag(TestTags.SEARCH_FIELD).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.SEARCH_FIELD).performClick()
        composeRule.onNodeWithTag(TestTags.SEARCH_FIELD).assertIsFocused()
    }

    @Test
    fun searchField_enteredText_textIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(TestTags.SEARCH_FIELD).performTextInput("Text")
        composeRule.onNodeWithTag(TestTags.SEARCH_FIELD).assertTextEquals("Text")
    }

    @Test
    fun sortSection_clickToggle_isVisible() {
        composeRule.onNodeWithTag(TestTags.SORT_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag(TestTags.SORT_SECTION).assertIsDisplayed()
    }

    @Test
    fun sortSection_chooseOrder_orderChanged() {
        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_TYPE_TITLE_DESC).performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_TYPE_DATE_DESC).performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_TYPE_TITLE_ASC).performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_TYPE_DATE_ASC).performClick()
    }

    @Test
    fun sortSection_clickToggleTwoTimes_isNotVisible() {
        composeRule.onNodeWithTag(TestTags.SORT_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag(TestTags.SORT_SECTION).assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag(TestTags.SORT_SECTION).assertDoesNotExist()
    }

    @Test
    fun filterSection_chooseFilter_listFiltered() {
        composeRule.onNodeWithTag(TestTags.FILTER_SECTION).assertIsDisplayed()
        composeRule.onNodeWithTag(Genre.Action.value).performClick()
        composeRule.waitUntil(1000L) { true }
        composeRule.onNodeWithTag(Genre.Adventure.value).performClick()
        composeRule.waitUntil(1000L) { true }
        composeRule.onNodeWithTag(TestTags.FILTER_SECTION).performScrollToNode(hasTestTag(Genre.Comedy.value))
        composeRule.waitUntil(1000L) { true }
        composeRule.onNodeWithTag(Genre.Comedy.value).performClick()
        composeRule.waitUntil(1000L) { true }
        composeRule.onNodeWithTag(TestTags.FILTER_SECTION).performScrollToNode(hasTestTag(Genre.Fantasy.value))
        composeRule.waitUntil(1000L) { true }
        composeRule.onNodeWithTag(Genre.Fantasy.value).performClick()
        composeRule.waitUntil(1000L) { true }
        composeRule.onNodeWithTag(TestTags.FILTER_SECTION).performScrollToNode(hasTestTag(Genre.Historical.value))
        composeRule.waitUntil(1000L) { true }
        composeRule.onNodeWithTag(Genre.Historical.value).performClick()
        composeRule.waitUntil(1000L) { true }
        composeRule.onNodeWithTag(TestTags.FILTER_SECTION).performScrollToNode(hasTestTag(Genre.Horror.value))
        composeRule.waitUntil(1000L) { true }
        composeRule.onNodeWithTag(Genre.Horror.value).performClick()
        composeRule.waitUntil(1000L) { true }
        composeRule.onNodeWithTag(TestTags.FILTER_SECTION).performScrollToNode(hasTestTag(Genre.Nonfiction.value))
        composeRule.waitUntil(1000L) { true }
        composeRule.onNodeWithTag(Genre.Nonfiction.value).performClick()
        composeRule.waitUntil(1000L) { true }
        composeRule.onNodeWithTag(TestTags.FILTER_SECTION).performScrollToNode(hasTestTag(Genre.Mystery.value))
        composeRule.waitUntil(1000L) { true }
        composeRule.onNodeWithTag(Genre.Mystery.value).performClick()
        composeRule.waitUntil(1000L) { true }
        composeRule.onNodeWithTag(TestTags.FILTER_SECTION).performScrollToNode(hasTestTag(Genre.Romance.value))
        composeRule.waitUntil(1000L) { true }
        composeRule.onNodeWithTag(Genre.Romance.value).performClick()
        composeRule.waitUntil(1000L) { true }
        composeRule.onNodeWithTag(TestTags.FILTER_SECTION).performScrollToNode(hasTestTag(Genre.Science.value))
        composeRule.waitUntil(1000L) { true }
        composeRule.onNodeWithTag(Genre.Science.value).performClick()
        composeRule.waitUntil(1000L) { true }
        composeRule.onNodeWithTag(TestTags.FILTER_SECTION).performScrollToNode(hasTestTag(Genre.Thriller.value))
        composeRule.waitUntil(1000L) { true }
        composeRule.onNodeWithTag(Genre.Thriller.value).performClick()
        composeRule.waitUntil(1000L) { true }
        composeRule.onNodeWithTag(TestTags.FILTER_SECTION).performScrollToNode(hasTestTag(Genre.All.value))
        composeRule.waitUntil(1000L) { true }
        composeRule.onNodeWithTag(Genre.All.value).performClick()
    }
}