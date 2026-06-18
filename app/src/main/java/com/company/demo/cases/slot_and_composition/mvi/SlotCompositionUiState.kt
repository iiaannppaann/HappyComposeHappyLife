package com.company.demo.cases.slot_and_composition.mvi

data class SlotCompositionUiState(
    val title: String = "Slot API 示範",
    val description: String = "展示 Compose 如何透過 Lambda 傳遞 Composable 達成極高的擴充性。"
)
