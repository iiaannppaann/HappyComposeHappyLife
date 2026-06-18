package com.company.demo.cases.slot_and_composition.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun SlotCompositionScreen(
    title: String,
    description: String,
    // 核心亮點：保留最下方的空間，讓外面決定要塞什麼按鈕、怎麼排列
    ctaRow: @Composable () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally // 上半部元件一律置中
        ) {
            // 1. 固定大小的圖片（用紅色的 Box 代表）
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color.Red, shape = RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 2. 說明文字標題與內文
            Text(text = title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = description, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)

            Spacer(modifier = Modifier.height(24.dp))

            // 3. 底部按鈕區：直接把外面傳進來的畫面填進來
            ctaRow()
        }
    }
}

@Composable
fun SimpleInfoScreen() {
    SlotCompositionScreen(
        title = "註冊成功",
        description = "歡迎加入我們！現在您可以開始探索完整的功能了。",
        ctaRow = {
            // 這裏是傳進去 catRow 的 Composable
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(onClick = { /* 關閉視窗 */ }) {
                    Text("我知道了")
                }
            }
        }
    )
}

@Composable
fun ComplexActionScreen() {
    SlotCompositionScreen(
        title = "偵測到安全性風險",
        description = "系統發現您的帳號有異常登入活動，請選擇接下來的操作。",
        ctaRow = {
            // 這裏是傳進去 catRow 的 Composable，我們利用 Row 來排版
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 1. 置左的小按鈕 (文字少、大小隨內容延伸)
                TextButton(onClick = { /* 忽略 */ }) {
                    Text("忽略提示", color = Color.Gray)
                }

                Spacer(modifier = Modifier.weight(1f)) // 頂出左右空間

                // 2. 置中的中型按鈕
                OutlinedButton(onClick = { /* 檢查日誌 */ }) {
                    Text("查看日誌")
                }

                Spacer(modifier = Modifier.width(8.dp))

                // 3. 右側的大按鈕 (寬度佔比大，使用主要顏色)
                Button(
                    onClick = { /* 修改密碼 */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                ) {
                    Text("立刻修改密碼")
                }
            }
        }
    )
}

@Composable
fun ZebraActionScreen() {
    val barList = List(20) { if (it % 2 == 0) Color.Black else Color.White }
    SlotCompositionScreen(
        title = "偵測到安全性風險",
        description = "系統發現您的帳號有異常登入活動，請選擇接下來的操作。",
        ctaRow = {
            Row(modifier = Modifier.fillMaxWidth()) {
                barList.forEach {
                    Box(modifier = Modifier
                        .height(20.dp)
                        .weight(1f)
                        .background(it))
                }
            }
        }
    )
}

@Composable
fun ZebraAnimActionScreen() {
    val barList = List(50) { if (it % 2 == 0) Color.Black else Color.White }
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        while (true) {
            scrollState.animateScrollTo(scrollState.maxValue, androidx.compose.animation.core.tween(2000))
            delay(500)
            scrollState.animateScrollTo(0, androidx.compose.animation.core.tween(2000))
            delay(500)
        }
    }

    SlotCompositionScreen(
        title = "無限捲動斑馬線",
        description = "這是一個利用 Slot 傳入自定義動畫捲動元件的範例。",
        ctaRow = {
            Row(modifier = Modifier.fillMaxWidth().horizontalScroll(scrollState, enabled = false)) {
                barList.forEach {
                    Box(modifier = Modifier
                        .height(20.dp)
                        .width(20.dp)
                        .background(it))
                }
            }
        }
    )
}

@Preview(showBackground = true, name = "Simple Info")
@Composable
fun PreviewSimpleInfoScreen() {
    SimpleInfoScreen()
}

@Preview(showBackground = true, name = "Complex Action")
@Composable
fun PreviewComplexActionScreen() {
    ComplexActionScreen()
}

@Preview(showBackground = true, name = "Zebra Action")
@Composable
fun PreviewZebraActionScreen() {
    ZebraActionScreen()
}

@Preview(showBackground = true, name = "Zebra Animation")
@Composable
fun PreviewZebraAnimActionScreen() {
    ZebraAnimActionScreen()
}
