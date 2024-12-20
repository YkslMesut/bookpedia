package com.myu.bookpedia.book.data.network

import com.myu.bookpedia.book.data.dto.SearchedResponseDto
import com.myu.bookpedia.core.data.safeCall
import com.myu.bookpedia.core.domain.DataError
import com.myu.bookpedia.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://openlibrary.org"

class KtorRemoteBookDataSource(private val httpClient: HttpClient) : RemoteBookDataSource {
    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?
    ): Result<SearchedResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "$BASE_URL/search.json"
            ) {
                parameter("q", query)
                parameter("limit", resultLimit)
                parameter("language", "eng")
                parameter(
                    "fields",
                    "key,title,author_name,author_key,cover_edition_key,cover_i,ratings_average,ratings_count,first_publish_year,language,number_of_pages_median,edition_count"
                )
            }
        }
    }
}