package com.myu.bookpedia.book.data.network

import com.myu.bookpedia.book.data.dto.BookWorkDto
import com.myu.bookpedia.book.data.dto.SearchedResponseDto
import com.myu.bookpedia.core.domain.DataError
import com.myu.bookpedia.core.domain.Result

interface RemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null
    ): Result<SearchedResponseDto, DataError.Remote>

    suspend fun getBookDetails(
        bookWorkId: String
    ): Result<BookWorkDto, DataError.Remote>
}