package com.mirza.stockpricetracker.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirza.stockpricetracker.core.logging.AppLogger
import com.mirza.stockpricetracker.domain.usecase.GetWelcomeMessageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val message: String = "",
    val isLoading: Boolean = false
)

sealed interface HomeIntent {
    data object Load : HomeIntent
}

class HomeViewModel(
    private val getWelcomeMessage: GetWelcomeMessageUseCase,
    private val appLogger: AppLogger
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    init {
        onIntent(HomeIntent.Load)
    }

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.Load -> load()
        }
    }

    private fun load() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            runCatching { getWelcomeMessage() }
                .onSuccess { message ->
                    _state.update { it.copy(message = message, isLoading = false) }
                }
                .onFailure { e ->
                    appLogger.e(TAG, "Failed to load welcome message", e)
                    _state.update { it.copy(isLoading = false) }
                }
        }
    }

    private companion object {
        const val TAG = "HomeViewModel"
    }
}
