package com.company.demo.cases.multitype.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MultiTypeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MultiTypeUiState())
    val uiState: StateFlow<MultiTypeUiState> = _uiState.asStateFlow()
}
