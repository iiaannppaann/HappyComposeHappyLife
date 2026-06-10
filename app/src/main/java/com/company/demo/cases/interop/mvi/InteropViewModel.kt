package com.company.demo.cases.interop.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InteropViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(InteropUiState())
    val uiState: StateFlow<InteropUiState> = _uiState.asStateFlow()

    // 增加計數值
    fun incrementCount() {
        _uiState.update { it.copy(count = it.count + 1) }
    }
}
