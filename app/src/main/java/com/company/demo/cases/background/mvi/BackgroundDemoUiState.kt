package com.company.demo.cases.background.mvi

data class BackgroundDemoCard(
    val id: Int,
    val text: String,
    val cornerRadiusDp: Int,
    val borderWidthDp: Int,
    val borderColorHex: String,
    val backgroundColorHex: String,
    val textColorHex: String
)

data class BackgroundDemoUiState(
    val cards: List<BackgroundDemoCard> = emptyList()
)
