package com.company.demo.cases.tabs.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.company.demo.cases.tabs.mvi.TabsUiState

@Composable
fun TabsComposeScreen(
    uiState: TabsUiState,
    onTabSelected: (Int) -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { uiState.tabTitles.size }
    )

    // 監聽 Pager 的左右滑動，同步回 MVI 的選取狀態
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            onTabSelected(page)
        }
    }

    // 監聽 MVI 狀態變更，同步 Pager 頁面滾動
    LaunchedEffect(uiState.selectedIndex) {
        if (pagerState.currentPage != uiState.selectedIndex) {
            pagerState.animateScrollToPage(uiState.selectedIndex)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Compose 簡潔的聲明式 TabRow
        TabRow(
            selectedTabIndex = uiState.selectedIndex,
            modifier = Modifier.fillMaxWidth()
        ) {
            uiState.tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = uiState.selectedIndex == index,
                    onClick = { onTabSelected(index) },
                    text = { Text(text = title) }
                )
            }
        }

        // Compose 內建的 HorizontalPager 橫向分頁
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) { page ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "內容：${uiState.tabTitles[page]}")
            }
        }
    }
}
