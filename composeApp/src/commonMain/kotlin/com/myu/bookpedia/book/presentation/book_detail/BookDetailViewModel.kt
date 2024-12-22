package com.myu.bookpedia.book.presentation.book_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.myu.bookpedia.app.Route
import com.myu.bookpedia.book.domain.repository.BookRepository
import com.myu.bookpedia.core.domain.onError
import com.myu.bookpedia.core.domain.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val bookRepository: BookRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val bookId = savedStateHandle.toRoute<Route.BookDetail>().id

    private val _state = MutableStateFlow(BookDetailState())
    val state = _state.onStart {
        fetchBookDescription()
        observeFavoriteStatus()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), _state.value)

    fun onAction(action: BookDetailAction) {
        when (action) {
            BookDetailAction.OnBackClick -> {}
            BookDetailAction.OnFavoriteClick -> {
                viewModelScope.launch {
                    if (state.value.isFavorite) {
                        bookRepository.deleteFromFavorite(bookId)
                    } else {
                        state.value.book?.let { bookRepository.markAsFavorite(it) }
                    }
                }
            }

            is BookDetailAction.OnSelectedBookChange -> {
                _state.update { currentState ->
                    currentState.copy(book = action.book)
                }
            }
        }
    }

    private fun observeFavoriteStatus() {
        bookRepository
            .isBookFavorite(bookId)
            .onEach { isFavorite ->
                _state.update { currentState ->
                    currentState.copy(isFavorite = isFavorite)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun fetchBookDescription() {
        viewModelScope.launch {
            bookRepository.getBookDescription(bookId)
                .onSuccess { description ->
                    _state.update { currentState ->
                        currentState.copy(
                            book = currentState.book?.copy(description = description),
                            isLoading = false
                        )
                    }
                }
                .onError {
                    _state.update { currentState ->
                        currentState.copy(
                            isLoading = false
                        )
                    }
                }
        }
    }
}