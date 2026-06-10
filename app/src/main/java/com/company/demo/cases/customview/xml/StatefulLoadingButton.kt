package com.company.demo.cases.customview.xml

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.company.demo.cases.customview.mvi.CustomViewDemoState
import com.company.demo.databinding.ViewStatefulLoadingButtonBinding

/**
 * 傳統 Android 中的自訂視圖 (Custom View)
 * 使用 ViewBinding 進行佈局綁定，徹底消除 findViewById 的低效與繁瑣。
 */
class StatefulLoadingButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    // 對於使用 <merge> 的佈局，ViewBinding 的 inflate 只需傳入 (inflater, parent) 兩個參數
    private val binding = ViewStatefulLoadingButtonBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    // 更新狀態與內容的方法
    fun setState(state: CustomViewDemoState, text: String) {
        binding.tvBtnText.text = text
        
        val isIdle = state == CustomViewDemoState.Idle
        binding.btnCardSubmit.isEnabled = isIdle
        binding.btnCardSubmit.alpha = if (isIdle) 1.0f else 0.6f

        when (state) {
            is CustomViewDemoState.Idle -> {
                binding.progressLoading.visibility = GONE
                binding.imgSuccess.visibility = GONE
            }
            is CustomViewDemoState.Loading -> {
                binding.progressLoading.visibility = VISIBLE
                binding.imgSuccess.visibility = GONE
            }
            is CustomViewDemoState.Success -> {
                binding.progressLoading.visibility = GONE
                binding.imgSuccess.visibility = VISIBLE
            }
        }
    }

    // 點擊事件轉發至卡片按鈕
    override fun setOnClickListener(l: OnClickListener?) {
        binding.btnCardSubmit.setOnClickListener(l)
    }
}
