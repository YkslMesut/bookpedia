package com.myu.bookpedia.book.domain

import com.myu.bookpedia.core.domain.DataError
import com.myu.bookpedia.core.domain.Result

interface BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
}