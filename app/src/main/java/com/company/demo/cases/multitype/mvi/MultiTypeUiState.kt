package com.company.demo.cases.multitype.mvi

sealed interface ListItem {
    val id: String
}

// 1. 一般 Plain 文字項目
data class PlainTextItem(
    override val id: String,
    val text: String
) : ListItem

// 2. 有背景顏色的文字項目
data class ColorTextItem(
    override val id: String,
    val text: String
) : ListItem

// 3. 斜體字型的文字項目
data class ItalicTextItem(
    override val id: String,
    val text: String
) : ListItem

data class MultiTypeUiState(
    // 產生大量假資料，確保清單長度可以順暢捲動展示
    val items: List<ListItem> = List(30) { index ->
        val id = "item_$index"
        when (index % 3) {
            0 -> PlainTextItem(id, "【一般樣式】項目編號 $index — 這是預設的 Plain 樣式文字")
            1 -> ColorTextItem(id, "【背景顏色】項目編號 $index — 這是帶有粉色背景的特色文字")
            else -> ItalicTextItem(id, "【斜體樣式】項目編號 $index — 這是套用了 Italic 斜體字型")
        }
    }
)
