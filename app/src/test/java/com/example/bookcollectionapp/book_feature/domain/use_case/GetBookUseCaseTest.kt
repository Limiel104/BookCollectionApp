package com.example.bookcollectionapp.book_feature.domain.use_case

import com.example.bookcollectionapp.book_feature.data.repository.FakeBookRepository
import com.example.bookcollectionapp.book_feature.domain.model.Book
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class GetBookUseCaseTest {

    private lateinit var getBookUseCase: GetBookUseCase
    private lateinit var fakeBookRepository: FakeBookRepository

    @Before
    fun setUp() {
        fakeBookRepository = FakeBookRepository()
        getBookUseCase = GetBookUseCase(fakeBookRepository)

        val booksToInsert = mutableListOf<Book>()

        val titles = listOf(
            "My new book",
            "Polish for dummies",
            "emmanuel",
        )

        val authors = listOf(
            "John Smith",
            "Kate Doe",
            "John Kruger",
        )

        val publishers = listOf(
            "Sign",
            "Book Media",
            "Fine Books",
        )

        val genres = listOf(
            "Mystery",
            "Fantasy",
            "Adventure",
        )

        for(i in 0..2) {
            booksToInsert.add(
                Book(
                    id = i+1,
                    dateAdded = i.toLong(),
                    title = titles[i],
                    author = authors[i],
                    publisher = publishers[i],
                    genre = genres[i],
                    imagePath = "/file.jpeg",
                    imageFileName = "file.jpeg",
                    readingStatus = "Completed",
                    rating = 5,
                    language = "Polish"
                )
            )
        }

        runBlocking {
            booksToInsert.forEach { fakeBookRepository.insertBook(it) }
        }
    }

    @Test
    fun bookList_containsBookWithSpecifiedId_returnBook() = runBlocking(){
        val id = 3
        val book = getBookUseCase(id)

        assertThat(book?.id).isEqualTo(id)
    }

    @Test
    fun bookList_containsBookWithSpecifiedId_returnNull() = runBlocking(){
        val id = 5
        val book = getBookUseCase(id)

        assertThat(book).isNull()
    }
}