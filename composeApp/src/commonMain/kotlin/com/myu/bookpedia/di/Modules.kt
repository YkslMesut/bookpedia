package com.myu.bookpedia.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.myu.bookpedia.book.data.database.DatabaseFactory
import com.myu.bookpedia.book.data.database.FavoriteBookDataBase
import com.myu.bookpedia.book.data.network.KtorRemoteBookDataSource
import com.myu.bookpedia.book.data.network.RemoteBookDataSource
import com.myu.bookpedia.book.data.repository.DefaultBookRepository
import com.myu.bookpedia.book.domain.repository.BookRepository
import com.myu.bookpedia.book.presentation.SelectedBookViewModel
import com.myu.bookpedia.book.presentation.book_detail.BookDetailViewModel
import com.myu.bookpedia.book.presentation.book_list.BookListViewModel
import com.myu.bookpedia.core.data.HttpClientFactory
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: org.koin.core.module.Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::DefaultBookRepository).bind<BookRepository>()

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver()).build()
    }
    single { get<FavoriteBookDataBase>().favoriteBookDao }

    viewModelOf(::BookListViewModel)
    viewModelOf(::SelectedBookViewModel)
    viewModelOf(::BookDetailViewModel)
}