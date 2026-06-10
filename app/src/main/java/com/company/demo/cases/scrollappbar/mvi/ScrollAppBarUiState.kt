package com.company.demo.cases.scrollappbar.mvi

data class ScrollAppBarUiState(
    val posts: List<String> = List(30) { "這是第 ${it + 1} 篇動態貼文，用來撐開高度以便捲動測試。" }
)
