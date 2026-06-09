package com.company.demo.cases.nested.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NestedDemoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(NestedDemoUiState())
    val uiState: StateFlow<NestedDemoUiState> = _uiState.asStateFlow()
}
