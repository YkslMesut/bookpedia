package com.myu.bookpedia.book.presentation.book_detail

import com.myu.bookpedia.book.domain.Book

data class BookDetailState(
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false,
    val book: Book? = null
)