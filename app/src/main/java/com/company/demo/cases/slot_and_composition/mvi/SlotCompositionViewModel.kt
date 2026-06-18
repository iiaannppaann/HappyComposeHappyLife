package com.company.demo.cases.slot_and_composition.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SlotCompositionViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SlotCompositionUiState())
    val uiState: StateFlow<SlotCompositionUiState> = _uiState.asStateFlow()
}
