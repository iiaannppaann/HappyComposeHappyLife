package com.company.demo.cases.background.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BackgroundDemoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BackgroundDemoUiState())
    val uiState: StateFlow<BackgroundDemoUiState> = _uiState.asStateFlow()

    init {
        loadCards()
    }

    private fun loadCards() {
        _uiState.value = BackgroundDemoUiState(
            cards = listOf(
                BackgroundDemoCard(
                    id = 1,
                    text = "經典圓角按鈕",
                    cornerRadiusDp = 8,
                    borderWidthDp = 0,
                    borderColorHex = "#00000000",
                    backgroundColorHex = "#FF6200EE", // 紫色 500
                    textColorHex = "#FFFFFFFF"
                ),
                BackgroundDemoCard(
                    id = 2,
                    text = "藥丸形外框按鈕",
                    cornerRadiusDp = 100,
                    borderWidthDp = 2,
                    borderColorHex = "#FF018786", // 藍綠色 700
                    backgroundColorHex = "#00000000", // 透明
                    textColorHex = "#FF018786"
                )
            )
        )
    }
}
