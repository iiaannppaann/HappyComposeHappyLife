package com.company.demo.cases.multitype.mvi

sealed interface ListItem {
    val id: String
}

data class HeaderItem(
    override val id: String,
    val title: String
) : ListItem

data class TextItem(
    override val id: String,
    val text: String
) : ListItem

data class MultiTypeUiState(
    val items: List<ListItem> = listOf(
        HeaderItem("h1", "程式語言"),
        TextItem("t1", "Kotlin"),
        TextItem("t2", "Java"),
        HeaderItem("h2", "使用者介面"),
        TextItem("t3", "Jetpack Compose"),
        TextItem("t4", "Android XML")
    )
)
