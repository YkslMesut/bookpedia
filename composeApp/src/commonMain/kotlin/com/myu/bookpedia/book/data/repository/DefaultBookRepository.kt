package com.myu.bookpedia.book.data.repository

import com.myu.bookpedia.book.data.mappers.toBook
import com.myu.bookpedia.book.data.network.RemoteBookDataSource
import com.myu.bookpedia.book.domain.Book
import com.myu.bookpedia.book.domain.BookRepository
import com.myu.bookpedia.core.domain.DataError
import com.myu.bookpedia.core.domain.Result
import com.myu.bookpedia.core.domain.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource
) : BookRepository {
    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource.searchBooks(query)
            .map { dto ->
                dto.results.map { it.toBook() }
            }
    }
}