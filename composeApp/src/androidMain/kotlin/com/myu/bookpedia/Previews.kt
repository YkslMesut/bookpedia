package com.myu.bookpedia

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.myu.bookpedia.book.domain.Book
import com.myu.bookpedia.book.presentation.book_list.BookListScreen
import com.myu.bookpedia.book.presentation.book_list.BookListState
import com.myu.bookpedia.book.presentation.book_list.components.BookSearchBar

@Preview
@Composable
private fun BookSearchBarPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
    ) {
        BookSearchBar(
            searchQuery = "",
            onSearchQueryChange = {},
            onImeSearch = { },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

private val books = (1..100).map {
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

@Preview
@Composable
private fun BookListScreenPreview() {
    BookListScreen(state = BookListState(
        searchResults = books,
    ), onAction = {})
}
