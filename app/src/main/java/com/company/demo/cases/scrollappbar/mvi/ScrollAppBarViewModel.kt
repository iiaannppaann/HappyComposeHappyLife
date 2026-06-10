package com.company.demo.cases.scrollappbar.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ScrollAppBarViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ScrollAppBarUiState())
    val uiState: StateFlow<ScrollAppBarUiState> = _uiState.asStateFlow()
}
