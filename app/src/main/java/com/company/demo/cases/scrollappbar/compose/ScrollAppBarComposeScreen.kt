package com.company.demo.cases.scrollappbar.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.demo.cases.scrollappbar.mvi.ScrollAppBarUiState

@Composable
fun ScrollAppBarComposeScreen(
    uiState: ScrollAppBarUiState
) {
    // 1. 核心狀態：記住 LazyColumn 的捲動狀態
    val listState = rememberLazyListState()

    // 2. 核心邏輯：宣告式的「狀態推導 (Derived State)」
    // 只要第一個元素 (假頭像區) 離開了畫面頂端 (index > 0)，就將狀態設為 true
    val showTopBar by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F5F5))) {
        
        // 內容滾動區
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize()
        ) {
            // 第 0 項：假頭像/簡介區塊 (一塊單色填滿的 Box)
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .background(Color(0xFFE0E0E0)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "這是一個假頭像/封面區域\n請向下捲動以顯示 Top Bar",
                        color = Color.DarkGray,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }

            // 其他動態貼文
            items(uiState.posts) { post ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Text(text = post, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider(color = Color(0xFFEEEEEE))
                }
            }
        }

        // 3. 核心動畫：利用 AnimatedVisibility 全自動處理進退場
        AnimatedVisibility(
            visible = showTopBar,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically(),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            // 置頂的 App Bar 樣式
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 左側佔位，為了讓中間文字絕對置中 (利用 weight)
                Spacer(modifier = Modifier.weight(1f))

                // 中央名稱
                Text(
                    text = "OpenId: 多米多羅",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black
                )

                // 右側按鈕，利用 weight(1f) 和 wrapContentWidth(Alignment.End) 推到底
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Button(
                        onClick = { /* 關注邏輯 */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text(text = "點我關注", color = Color.White)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScrollAppBarComposeScreen() {
    MaterialTheme {
        ScrollAppBarComposeScreen(ScrollAppBarUiState())
    }
}
