package com.myu.bookpedia.book.domain.repository

import com.myu.bookpedia.book.domain.Book
import com.myu.bookpedia.core.domain.DataError
import com.myu.bookpedia.core.domain.Result

interface BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
    suspend fun getBookDescription(bookId: String): Result<String?, DataError.Remote>
}