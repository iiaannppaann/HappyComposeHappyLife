package com.company.demo.cases.tabs.mvi

data class TabsUiState(
    val selectedIndex: Int = 0,
    val tabTitles: List<String> = listOf("分頁一", "分頁二", "分頁三")
)
