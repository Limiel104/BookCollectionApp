package com.example.bookcollectionapp.book_feature.domain.use_case

import com.example.bookcollectionapp.book_feature.data.repository.FakeBookRepository
import com.example.bookcollectionapp.book_feature.domain.model.Book
import com.example.bookcollectionapp.book_feature.domain.util.BookOrder
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetBooksUseCaseTest {

    private lateinit var getBooksUseCase: GetBooksUseCase
    private lateinit var fakeBookRepository: FakeBookRepository

    @Before
    fun setUp() {
        fakeBookRepository = FakeBookRepository()
        getBooksUseCase = GetBooksUseCase(fakeBookRepository)

        val booksToInsert = mutableListOf<Book>()

        val titles = listOf(
            "My new book",
            "Polish for dummies",
            "emmanuel smith and his strange ideas",
            "Fantastic life of Smith family",
            "Happiness is the best",
            "Basics of biology"
        )

        val authors = listOf(
            "John Smith",
            "Kate Doe",
            "John Kruger",
            "Robert Hat",
            "Hannah Fine",
            "Anne Smith"
        )

        val publishers = listOf(
            "Sign",
            "Book Media",
            "Fine Books",
            "Sign",
            "Book Library",
            "GoodReads"
        )

        val genres = listOf(
            "Mystery",
            "Fantasy",
            "Adventure",
            "Mystery",
            "Comedy",
            "Thriller"
        )

        for(i in 0..5) {
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
    fun bookList_orderByTitleAscending_correctOrder() = runBlocking {
        val books = getBooksUseCase(
            bookOrder = BookOrder.TitleAscending(),
            query = "",
            filter = ""
        ).first()

        for(i in 0..books.size-2) {
            assertThat(books[i].title.lowercase()).isLessThan(books[i+1].title.lowercase())
        }
    }

    @Test
    fun bookList_OrderByTitleDescending_CorrectOrder() = runBlocking {
        val books = getBooksUseCase(
            bookOrder = BookOrder.TitleDescending(),
            query = "",
            filter = ""
        ).first()

        for(i in 0..books.size-2) {
            assertThat(books[i].title.lowercase()).isGreaterThan(books[i+1].title.lowercase())
        }
    }

    @Test
    fun bookList_OrderByDateAscending_CorrectOrder() = runBlocking {
        val books = getBooksUseCase(
            bookOrder = BookOrder.DateAscending(),
            query = "",
            filter = ""
        ).first()

        for(i in 0..books.size-2) {
            assertThat(books[i].dateAdded).isLessThan(books[i+1].dateAdded)
        }
    }

    @Test
    fun bookList_OrderByDateDescending_CorrectOrder() = runBlocking {
        val books = getBooksUseCase(
            bookOrder = BookOrder.DateDescending(),
            query = "",
            filter = ""
        ).first()

        for(i in 0..books.size-2) {
            assertThat(books[i].dateAdded).isGreaterThan(books[i+1].dateAdded)
        }
    }

    @Test
    fun bookList_filterBookList_resultListIsNotEmpty() = runBlocking {
        val filter = "Mystery"
        val books = getBooksUseCase(
            bookOrder = BookOrder.TitleAscending(),
            query = "",
            filter = filter
        ).first()

        assertThat(books).isNotEmpty()
    }

    @Test
    fun bookList_filterBookList_resultListIsEmpty() = runBlocking {
        val filter = "Action"
        val books = getBooksUseCase(
            bookOrder = BookOrder.TitleAscending(),
            query = "",
            filter = filter
        ).first()

        assertThat(books).isEmpty()
    }

    @Test
    fun bookList_filterBookList_orderByTitleAscending() = runBlocking {
        val filter = "Mystery"
        val books = getBooksUseCase(
            bookOrder = BookOrder.TitleAscending(),
            query = "",
            filter = filter
        ).first()

        for(i in 0..books.size-2) {
            assertThat(books[i].title.lowercase()).isLessThan(books[i+1].title.lowercase())
        }
    }

    @Test
    fun bookList_filterBookList_orderByTitleDescending() = runBlocking {
        val filter = "Mystery"
        val books = getBooksUseCase(
            bookOrder = BookOrder.TitleDescending(),
            query = "",
            filter = filter
        ).first()

        for(i in 0..books.size-2) {
            assertThat(books[i].title.lowercase()).isGreaterThan(books[i+1].title.lowercase())
        }
    }

    @Test
    fun bookList_filterBookList_orderByDateAscending() = runBlocking {
        val filter = "Mystery"
        val books = getBooksUseCase(
            bookOrder = BookOrder.DateAscending(),
            query = "",
            filter = filter
        ).first()

        for(i in 0..books.size-2) {
            assertThat(books[i].dateAdded).isLessThan(books[i+1].dateAdded)
        }
    }

    @Test
    fun bookList_filterBookList_orderByDateDescending() = runBlocking {
        val filter = "Mystery"
        val books = getBooksUseCase(
            bookOrder = BookOrder.DateDescending(),
            query = "",
            filter = filter
        ).first()

        for(i in 0..books.size-2) {
            assertThat(books[i].dateAdded).isGreaterThan(books[i+1].dateAdded)
        }
    }

    @Test
    fun bookList_searchBookList_resultListIsNotEmpty() = runBlocking {
        val query = "SMITH"
        val books = getBooksUseCase(
            bookOrder = BookOrder.TitleAscending(),
            query = query,
            filter = ""
        ).first()

        assertThat(books).isNotEmpty()
    }

    @Test
    fun bookList_searchBookList_resultListIsEmpty() = runBlocking {
        val query = "Great family"
        val books = getBooksUseCase(
            bookOrder = BookOrder.TitleAscending(),
            query = query,
            filter = ""
        ).first()

        assertThat(books).isEmpty()
    }

    @Test
    fun bookList_searchBookList_orderByTitleAscending() = runBlocking {
        val query = "SMITH"
        val books = getBooksUseCase(
            bookOrder = BookOrder.TitleAscending(),
            query = query,
            filter = ""
        ).first()

        for(i in 0..books.size-2) {
            assertThat(books[i].title.lowercase()).isLessThan(books[i+1].title.lowercase())
        }
    }

    @Test
    fun bookList_searchBookList_orderByTitleDescending() = runBlocking {
        val query = "SMITH"
        val books = getBooksUseCase(
            bookOrder = BookOrder.TitleDescending(),
            query = query,
            filter = ""
        ).first()

        for(i in 0..books.size-2) {
            assertThat(books[i].title.lowercase()).isGreaterThan(books[i+1].title.lowercase())
        }
    }

    @Test
    fun bookList_searchBookList_orderByDateAscending() = runBlocking {
        val query = "SMITH"
        val books = getBooksUseCase(
            bookOrder = BookOrder.DateAscending(),
            query = query,
            filter = ""
        ).first()

        for(i in 0..books.size-2) {
            assertThat(books[i].dateAdded).isLessThan(books[i+1].dateAdded)
        }
    }

    @Test
    fun bookList_searchBookList_orderByDateDescending() = runBlocking {
        val query = "SMITH"
        val books = getBooksUseCase(
            bookOrder = BookOrder.DateDescending(),
            query = query,
            filter = ""
        ).first()

        for(i in 0..books.size-2) {
            assertThat(books[i].dateAdded).isGreaterThan(books[i+1].dateAdded)
        }
    }

    @Test
    fun bookList_searchAndFilterBookList_resultListIsNotEmpty() = runBlocking {
        val query = "SMITH"
        val filter = "Mystery"
        val books = getBooksUseCase(
            bookOrder = BookOrder.TitleAscending(),
            query = query,
            filter = filter
        ).first()

        assertThat(books).isNotEmpty()

    }

    @Test
    fun bookList_searchAndFilterBookList_resultListIsEmpty() = runBlocking {
        val query = "SMITH"
        val filter = "Horror"
        val books = getBooksUseCase(
            bookOrder = BookOrder.TitleAscending(),
            query = query,
            filter = filter
        ).first()

        assertThat(books).isEmpty()

    }

    @Test
    fun bookList_searchAndFilterBookList_orderByTitleAscending() = runBlocking {
        val query = "SMITH"
        val filter = "Mystery"
        val books = getBooksUseCase(
            bookOrder = BookOrder.TitleAscending(),
            query = query,
            filter = filter
        ).first()

        for(i in 0..books.size-2) {
            assertThat(books[i].title.lowercase()).isLessThan(books[i+1].title.lowercase())
        }
    }

    @Test
    fun bookList_searchAndFilterBookList_orderByTitleDescending() = runBlocking {
        val query = "SMITH"
        val filter = "Mystery"
        val books = getBooksUseCase(
            bookOrder = BookOrder.TitleDescending(),
            query = query,
            filter = filter
        ).first()

        for(i in 0..books.size-2) {
            assertThat(books[i].title.lowercase()).isGreaterThan(books[i+1].title.lowercase())
        }
    }

    @Test
    fun bookList_searchAndFilterBookList_orderByDateAscending() = runBlocking {
        val query = "SMITH"
        val filter = "Mystery"
        val books = getBooksUseCase(
            bookOrder = BookOrder.DateAscending(),
            query = query,
            filter = filter
        ).first()

        for(i in 0..books.size-2) {
            assertThat(books[i].dateAdded).isLessThan(books[i+1].dateAdded)
        }
    }

    @Test
    fun bookList_searchAndFilterBookList_orderByDateDescending() = runBlocking {
        val query = "SMITH"
        val filter = "Mystery"
        val books = getBooksUseCase(
            bookOrder = BookOrder.DateDescending(),
            query = query,
            filter = filter
        ).first()

        for(i in 0..books.size-2) {
            assertThat(books[i].dateAdded).isGreaterThan(books[i+1].dateAdded)
        }
    }
}