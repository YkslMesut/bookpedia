package com.myu.bookpedia.book.presentation.book_list

import com.myu.bookpedia.book.domain.Book
import com.myu.bookpedia.core.presentation.UiText

data class BookListState(
    val searchQuery: String = "Kotlin",
    val searchResults: List<Book> = books,
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)

val books = (1..100).map {
    Book(
        id = it.toString(),
        title = "Book $it",
        imageUrl = "https://search.yahoo.com/search?p=quam",
        authors = listOf("Mesut Yüksel Usta"),
        description = "Description $it",
        languages = listOf(),
        firstPublishYear = null,
        averageRating = 4.674332,
        ratingCount = 5,
        numPages = 100,
        numEditions = 3
    )
}
