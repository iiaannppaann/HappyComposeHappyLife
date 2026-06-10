package com.company.demo.cases.customview.mvi

sealed class CustomViewDemoState {
    object Idle : CustomViewDemoState()
    object Loading : CustomViewDemoState()
    object Success : CustomViewDemoState()
}

data class CustomViewDemoUiState(
    val state: CustomViewDemoState = CustomViewDemoState.Idle,
    val text: String = "提交資料"
)
