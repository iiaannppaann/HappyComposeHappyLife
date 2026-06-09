# HappyCompose Project Instructions & Conventions

本文件記錄了本專案（HappyCompose）開發時必須嚴格遵守的架構規範與開發標準。

## 1. 語言與註釋規範 (Language & Commenting Standards)

* **所有程式碼註釋 (Code Comments) 必須使用繁體中文 (Traditional Chinese)**。
* **使用者介面上的對比說明文字 (User-Facing UI texts) 亦優先使用繁體中文**，以確保全體本地開發人員與使用者能獲得一致、清晰的體驗。
* 撰寫註釋時，力求精簡、精確，聚焦於解釋「為什麼這段程式碼要這樣設計」，而非只是描述程式碼的表面行為。

---

## 2. 比較案例開發架構 (Architectural & Navigation Patterns for Comparison Cases)

為解決代碼難以追蹤（hard to trace code）的問題，並確保 legacy XML 與 Jetpack Compose 的效能與實作對比足夠直觀，專案遵循以下架構準則：

### 🚫 禁止「單一容器動態切換」模式 (No Toggle Style Switcher)
* **禁止**在同一個螢幕/容器中混合使用 `AndroidView` 橋接與 Compose 視圖，並透過開關（Toggle Switch）動態互換的設計。
* 這種混雜模式會增加不必要的狀態複雜度，使得生命週期、依賴狀態和調用鏈變得難以追蹤。

###  採用「專屬獨立 Fragment」模式 (Dedicated Separate Fragments)
對於每一個 XML vs Compose 的對比案例，必須拆分為兩個**完全獨立且職責單一**的 Fragment：
1. **XML 版本 Fragment** (例如 `BackgroundDemoXmlFragment`):
  * 採用傳統 XML 佈局（如 `fragment_background_demo_xml.xml`），透過 ViewBinding 加載。
   * 將所有動態數值與樣式設定（如手動計算 density 並程式化設定 `GradientDrawable` 等）寫於 Fragment 內。
   * 藉此將 legacy XML 的高代價與高複雜度清晰地攤開，以便於比對。
2. **Compose 版本 Fragment** (例如 `BackgroundDemoComposeFragment`):
   * 完全使用 Jetpack Compose 開發。
   * 直接調用對應的純 Composable 畫面（例如 `BackgroundDemoComposeScreen`），由 inline `Modifier` 進行動態樣式渲染。

### 🧭 主選單直接跳轉設計 (Direct Main Menu Entry)
* 在主選單中（如 `MainMenuFragment`），每個比較卡片不應只有單一的點擊路徑，而是應提供兩個明確的按鈕：
  * **「XML Version」**：直接開啟 XML Fragment。
  * **「Compose Version」**：直接開啟 Compose Fragment。
* 這樣能讓開發者或測試者一目了然，隨意且乾淨地在兩個完全獨立的視圖之間進行切換，而不會干擾彼此的渲染機制。

---

### 📂 套件結構規範 (Package Structure Conventions)
為確保專案架構職責分明且易於追蹤，每個比較案例內部皆必須劃分並整理至以下子套件：
1. **`mvi/`**：存放狀態與視圖模型，例如 `XXXUiState.kt` 與 `XXXViewModel.kt`。
2. **`compose/`**：存放 Jetpack Compose 視圖層，例如 `XXXComposeFragment.kt` 與 `XXXComposeScreen.kt`。
3. **`xml/`**：存放傳統 XML 視圖層，例如 `XXXXmlFragment.kt`。
