package com.company.demo.cases.button.xml

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.company.demo.R
import com.company.demo.cases.button.mvi.ButtonState

/**
 * 傳統 Android 中的自訂視圖 (Custom View)
 * 用於封裝狀態按鈕的所有內部狀態與視覺樣式，達成跨 XML 版面的模組化與重複使用。
 * 這與 Jetpack Compose 中隨手編寫一個可重用的 @Composable 函數相比，
 * 自訂 View 需要大量的樣板程式碼，如多個構造函數、手動 inflate 佈局、手動綁定子視圖。
 */
class StatefulLoadingButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val progressLoading: ProgressBar
    private val imgSuccess: ImageView
    private val tvBtnText: TextView
    private val btnCardSubmit: View

    init {
        // 加載內部合併佈局
        LayoutInflater.from(context).inflate(R.layout.view_stateful_loading_button, this, true)
        
        progressLoading = findViewById(R.id.progressLoading)
        imgSuccess = findViewById(R.id.imgSuccess)
        tvBtnText = findViewById(R.id.tvBtnText)
        btnCardSubmit = findViewById(R.id.btnCardSubmit)
    }

    // 更新狀態與內容的方法
    fun setState(state: ButtonState, text: String) {
        tvBtnText.text = text
        
        val isIdle = state == ButtonState.Idle
        btnCardSubmit.isEnabled = isIdle
        btnCardSubmit.alpha = if (isIdle) 1.0f else 0.6f

        when (state) {
            is ButtonState.Idle -> {
                progressLoading.visibility = View.GONE
                imgSuccess.visibility = View.GONE
            }
            is ButtonState.Loading -> {
                progressLoading.visibility = View.VISIBLE
                imgSuccess.visibility = View.GONE
            }
            is ButtonState.Success -> {
                progressLoading.visibility = View.GONE
                imgSuccess.visibility = View.VISIBLE
            }
        }
    }

    // 點擊事件轉發至卡片按鈕
    override fun setOnClickListener(l: OnClickListener?) {
        btnCardSubmit.setOnClickListener(l)
    }
}
