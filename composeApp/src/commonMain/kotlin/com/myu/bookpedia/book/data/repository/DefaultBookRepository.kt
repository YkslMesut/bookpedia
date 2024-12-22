package com.myu.bookpedia.book.data.repository

import androidx.sqlite.SQLiteException
import com.myu.bookpedia.book.data.database.FavoriteBookDao
import com.myu.bookpedia.book.data.mappers.toBook
import com.myu.bookpedia.book.data.mappers.toBookEntity
import com.myu.bookpedia.book.data.network.RemoteBookDataSource
import com.myu.bookpedia.book.domain.Book
import com.myu.bookpedia.book.domain.repository.BookRepository
import com.myu.bookpedia.core.domain.DataError
import com.myu.bookpedia.core.domain.EmptyResult
import com.myu.bookpedia.core.domain.Result
import com.myu.bookpedia.core.domain.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val favoriteBookDao: FavoriteBookDao
) : BookRepository {
    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource.searchBooks(query)
            .map { dto ->
                dto.results.map { it.toBook() }
            }
    }

    override suspend fun getBookDescription(bookId: String): Result<String?, DataError.Remote> {
        val localResult = favoriteBookDao.getFavoriteBook(id = bookId)

        return if (localResult == null) {
            remoteBookDataSource.getBookDetails(bookWorkId = bookId).map { it.description }
        } else {
            Result.Success(localResult.description)
        }
    }

    override fun getFavoriteBooks(): Flow<List<Book>> {
        return favoriteBookDao.getFavoriteBooks().map { bookEntities ->
            bookEntities.map { it.toBook() }
        }
    }

    override fun isBookFavorite(id: String): Flow<Boolean> {
        return favoriteBookDao.getFavoriteBooks().map { bookEntities ->
            bookEntities.any { it.id == id }
        }
    }

    override suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local> {
        return try {
            favoriteBookDao.upsert(book.toBookEntity())
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteFromFavorite(id: String) {
        favoriteBookDao.deleteFavoriteBook(id)
    }
}