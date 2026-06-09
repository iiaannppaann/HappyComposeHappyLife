package com.company.demo.cases.nested.compose

import com.company.demo.cases.nested.mvi.NestedDemoUiState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NestedDemoComposeScreen(uiState: NestedDemoUiState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // 在 Compose 中，使用 10 層 Column 模擬一樣的巢狀深度做效能比對。
        // 得益於 Compose 的 O(N) 單次測量限制（Single Pass Measurement），
        // 即使層級再深，也不會發生傳統 XML 佈局中的指數級二次測量效能重災，依然能保持流暢。
        
        // Level 1
        Text(text = uiState.level1, modifier = Modifier.padding(bottom = 8.dp))
        
        // Level 2
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(text = uiState.level2, modifier = Modifier.padding(bottom = 8.dp))
            
            // Level 3
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(text = uiState.level3, modifier = Modifier.padding(bottom = 8.dp))
                
                // Level 4
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(text = uiState.level4, modifier = Modifier.padding(bottom = 8.dp))
                    
                    // Level 5
                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        Text(text = uiState.level5, modifier = Modifier.padding(bottom = 8.dp))
                        
                        // Level 6
                        Column(modifier = Modifier.padding(start = 16.dp)) {
                            Text(text = uiState.level6, modifier = Modifier.padding(bottom = 8.dp))
                            
                            // Level 7
                            Column(modifier = Modifier.padding(start = 16.dp)) {
                                Text(text = uiState.level7, modifier = Modifier.padding(bottom = 8.dp))
                                
                                // Level 8
                                Column(modifier = Modifier.padding(start = 16.dp)) {
                                    Text(text = uiState.level8, modifier = Modifier.padding(bottom = 8.dp))
                                    
                                    // Level 9
                                    Column(modifier = Modifier.padding(start = 16.dp)) {
                                        Text(text = uiState.level9, modifier = Modifier.padding(bottom = 8.dp))
                                        
                                        // Level 10
                                        Column(modifier = Modifier.padding(start = 16.dp)) {
                                            Text(text = uiState.level10, modifier = Modifier.padding(bottom = 8.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
