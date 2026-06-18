# HappyCompose 🚀

**HappyCompose** 是一個專為 Android 開發者設計的深度教學與效能對比專案。本專案透過「**Side-by-Side (並排對比)**」的方式，以極簡、純粹的視覺風格，直觀地展示 **Legacy Android XML 視圖系統** 與 **Jetpack Compose 宣告式 UI** 在開發體驗、架構複雜度以及底層效能上的巨大差異。

## 🎯 核心設計理念 (Core Architecture)

為了解決以往混合開發中「程式碼難以追蹤 (hard to trace code)」的問題，本專案堅守以下設計原則：
* **純粹隔離 (Dedicated Separate Fragments)**：我們絕對不在同一個畫面上使用開關（Toggle）動態切換 XML 與 Compose。相反地，每一個對比案例都被嚴格拆分為兩個**完全獨立的 Fragment**。
* **MVI 架構統一**：所有的畫面皆由同一套 `UiState` 與 `ViewModel` 驅動，確保雙方接收到完全相同的資料源，唯一的變數只有「**視圖層 (UI Layer) 的實作方式**」。
* **極簡設計**：移除所有不必要的文字顏色、字體大小、陰影與多餘裝飾，將焦點 100% 集中於「版面層級 (Hierarchy)」、「效能瓶頸 (Bottlenecks)」與「程式碼量 (Boilerplate)」。

---

## 📚 比較案例一覽 (Comparison Cases)

本專案收錄了 7 個經典且最具代表性的 Android 開發痛點，並附有 **Airbnb Showkase** 整合作為彩蛋。

### Case 1: Drawable Explosion (資源檔案爆炸)
展示傳統 XML 為了達成簡單的圓角、邊框或漸層，必須建立無數個外部 `bg_xxxx.xml` Drawable 檔案，造成資源目錄臃腫；對比之下，Compose 僅需透過行內的 `Modifier.background()` 或 `Modifier.border()` 即可優雅解決。

### Case 2: Nested Layout Penalty (巢狀版面效能懲罰) ⚠️ 效能極限測試
這是一個極度暴力的效能壓測：
* **XML 版本**：建構了 **10 層深度的 `LinearLayout`**，並內建了「5000 次同步重新量測 (`measure` & `layout`)」壓測按鈕。這會引發 Android 視圖樹指數級的二次測量 (Double Taxation)，**直接導致主執行緒卡死數秒 (ANR)**。
* **Compose 版本**：同樣建構了 10 層深的 `Column`，執行相同的 5000 次狀態變更。得益於 Compose 的 **Snapshot 狀態批次合併**與 **O(N) 單次測量 (Single Pass Measurement)** 機制，畫面能在幾毫秒內瞬間完成更新，完全不卡頓。

### Case 3: Dynamic Form Generation (動態表單生成)
展示當表單欄位需要根據資料動態增減時：
* **XML 版本**：必須痛苦地手動調用 `addView()` 與 `removeViews()`，並且手動管理 `TextWatcher` 生命週期與難以追蹤的 View ID。
* **Compose 版本**：僅需一個簡單的 `repeat(state.fields.size)` 迴圈，Compose 編譯器會自動為我們處理底層的增刪與狀態重組。

### Case 4: Multi-Type List (多視圖類型列表)
實作一個包含 3 種不同樣式 (Plain, Color, Italic) 的清單：
* **XML 版本**：需要龐大的 `RecyclerView.Adapter` 樣板程式碼，包含多個 `ViewHolder`、覆寫 `getItemViewType`，並且要維護多個獨立的 `item_xxx.xml`。
* **Compose 版本**：在 `LazyColumn` 內部，僅需使用 Kotlin 原生的 `when (item)` 模式匹配，一個檔案、幾十行程式碼即搞定多類型列表的渲染。

### Case 5: Stateful Loading Button (自訂狀態按鈕元件)
探討「可重複使用元件 (Reusable Components)」的封裝成本：
* **XML 版本**：需要建立一個繼承自 `FrameLayout` 的 Custom View 類別，處理多個 `Constructor`、使用 `<merge>` 標籤 inflate 佈局，再透過 `ViewBinding` 綁定狀態並改變子元件 (ProgressBar, ImageView) 的可見度。
* **Compose 版本**：就是寫一個帶有參數的 `@Composable fun StatefulLoadingButton()`，乾淨、無狀態 (Stateless)、立即可用。

### Case 6: Interoperability Showcase (混合互操作展示)
現實世界中的專案通常是漸進式遷移的，此案例展示雙向的無縫接軌：
* **XML in Compose**：在 Compose 宣告式樹狀結構中，透過 `AndroidView` 嵌入一個傳統的 Android `Button`。
* **Compose in XML**：在傳統的 XML 佈局中放置 `<androidx.compose.ui.platform.ComposeView>`，並在 Fragment 中呼叫 `setContent {}` 繪製 Compose 內容。兩者皆能共享同一個 MVI ViewModel。

### Case 7: Tabs & ViewPager (經典分頁滑動)
* **XML 版本**：使用 `ViewPager2` 搭配 `TabLayout` 與 `TabLayoutMediator`，並依然需要撰寫一個 `RecyclerView.Adapter` 才能讓三個分頁滑動。
* **Compose 版本**：使用官方內建的 `HorizontalPager` 與 `TabRow`，直接透過 `pagerState.animateScrollToPage` 與 `LaunchedEffect` 連動，邏輯清晰且零 Adapter 樣板程式碼。

### Case 8: Scroll-Aware App Bar (捲動連動頂部列)
展示個人主頁中常見的「滑過封面區域後，頂部浮現名稱與關注按鈕」的視覺互動：
* **XML 版本**：必須手動對 `RecyclerView` 加上 `OnScrollListener`，取得 `layoutManager.findFirstVisibleItemPosition()` 來判斷頭像區是否還在畫面上，接著「手動呼叫」`animate().alpha(1f)` 與控制 View 的 `Visibility` 狀態。這是極其典型的指令式寫法，一旦滑動過快或資料重置，極易產生閃爍與狀態脫鉤的 Bug。
* **Compose 版本**：完美展現「**狀態推導 (Derived State)**」的威力！只需宣告 `val showTopBar by remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }`，接著用 `AnimatedVisibility(visible = showTopBar)` 包覆 Top Bar。這行宣告完全取代了所有的捲動監聽與動畫控制，UI 永遠 100% 同步於狀態，優雅且不會出錯。

### Case 9: Slot & Composition API (元件插槽與組合)
探討「複合型佈局元件 (Compound Layout Components)」的擴充能力與彈性：
* **XML 版本**：為了達成可自訂底部動作區（Slot）的卡片，必須在共通佈局中建立一個 `FrameLayout` 容器，接著在 Fragment 內透過程式碼手動 `LayoutInflater.inflate` 不同的 View、處理多重 View ID 管理、手動處理複雜排版，甚至要手動管理 `ValueAnimator` 來達成斑馬線動畫效果。這會導致 Fragment 充斥大量指令式視圖控制的樣板程式碼，難以維護與重用。
* **Compose 版本**：僅需傳入一個極度優雅的 `ctaRow: @Composable () -> Unit` 函式。無論外面想要塞入置中單一按鈕、多重對齊按鈕、靜態斑馬線、甚至是包含 `LaunchedEffect` 與 `rememberScrollState` 的無限自定義捲動動畫，皆能在不更改卡片主體實作的前提下完美抽換，展現了宣告式 UI 「組合重於繼承」的極致威力。

---

## 🎁 Bonus: Airbnb Showkase Explorer
**真正的元件設計系統 (Design System) 體驗！**

專案已導入 **KSP (Kotlin Symbol Processing)** 與 **Airbnb Showkase**。我們為 `Case 5` 的 `StatefulLoadingButton` 撰寫了三個 `@Preview` (Idle, Loading, Success)。
您只需在 App 主選單最下方點擊 **「Open Showkase」**，即可啟動一個精美的元件型錄瀏覽器，自動彙整專案內所有的 Compose 預覽畫面，完美展現宣告式 UI 在開發工具鏈上的巨大優勢。

---

## 🛠️ 技術棧 (Tech Stack)
* **Language**: Kotlin 2.0+
* **UI**: Jetpack Compose (BOM) & Legacy XML (ViewBinding)
* **Architecture**: MVI (Model-View-Intent) via `StateFlow` & `ViewModel`
* **Navigation**: Fragment Transactions (To ensure strict lifecycle isolation)
* **Tooling**: KSP, Airbnb Showkase

---

**Happy Composing! 🎉**
