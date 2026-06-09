package com.company.demo.cases.nested.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NestedDemoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(NestedDemoUiState())
    val uiState: StateFlow<NestedDemoUiState> = _uiState.asStateFlow()

    // 用於 Compose 的高頻率狀態變更
    fun updateStressValue(value: Int, durationMs: Long = 0) {
        _uiState.update { currentState ->
            currentState.copy(
                level10 = "第 10 層 — 壓測次數: $value",
                stressResult = if (value == 5000) "壓測完成！耗時: ${durationMs}ms" else "壓測中..."
            )
        }
    }

    // 設定壓測結果字串
    fun setStressResult(result: String) {
        _uiState.update { currentState ->
            currentState.copy(stressResult = result)
        }
    }
}
