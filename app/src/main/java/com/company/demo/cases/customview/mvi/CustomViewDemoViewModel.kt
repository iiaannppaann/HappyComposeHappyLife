package com.company.demo.cases.customview.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CustomViewDemoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CustomViewDemoUiState())
    val uiState: StateFlow<CustomViewDemoUiState> = _uiState.asStateFlow()

    // 觸發提交按鈕的非同步處理
    fun submit() {
        if (_uiState.value.state != CustomViewDemoState.Idle) return

        viewModelScope.launch {
            // 進入讀取中狀態
            _uiState.update { it.copy(state = CustomViewDemoState.Loading, text = "處理中...") }
            
            // 模擬網路延遲 2 秒
            delay(2000)
            
            // 進入成功狀態
            _uiState.update { it.copy(state = CustomViewDemoState.Success, text = "提交成功") }
            
            // 2 秒後自動重設回初始狀態，方便重複測試
            delay(2000)
            _uiState.update { it.copy(state = CustomViewDemoState.Idle, text = "提交資料") }
        }
    }
}
