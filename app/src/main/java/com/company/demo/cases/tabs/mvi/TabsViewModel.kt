package com.company.demo.cases.tabs.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TabsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TabsUiState())
    val uiState: StateFlow<TabsUiState> = _uiState.asStateFlow()

    // 更新當前選取的的分頁索引
    fun selectTab(index: Int) {
        _uiState.update { it.copy(selectedIndex = index) }
    }
}
