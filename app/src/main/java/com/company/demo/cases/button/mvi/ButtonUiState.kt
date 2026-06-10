package com.company.demo.cases.button.mvi

sealed class ButtonState {
    object Idle : ButtonState()
    object Loading : ButtonState()
    object Success : ButtonState()
}

data class ButtonUiState(
    val state: ButtonState = ButtonState.Idle,
    val text: String = "提交資料"
)
