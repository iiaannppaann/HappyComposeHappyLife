package com.company.demo.cases.slot_and_composition.xml

import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.company.demo.databinding.FragmentSlotCompositionXmlBinding
import com.google.android.material.button.MaterialButton

class SlotCompositionXmlFragment : Fragment() {

    private lateinit var binding: FragmentSlotCompositionXmlBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSlotCompositionXmlBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupSimpleInfoCard()
        setupComplexActionCard()
        setupZebraActionCard()
        setupZebraAnimCard()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
        binding.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun setupSimpleInfoCard() {
        val card = binding.cardSimpleInfo
        card.tvTitle.text = "註冊成功"
        card.tvDescription.text = "歡迎加入我們！現在您可以開始探索完整的功能了。"
        
        // XML 必須手動建立 View 並加入到容器中
        val button = MaterialButton(requireContext()).apply {
            text = "我知道了"
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER
            }
        }
        card.ctaContainer.addView(button)
    }

    private fun setupComplexActionCard() {
        val card = binding.cardComplexAction
        card.tvTitle.text = "偵測到安全性風險"
        card.tvDescription.text = "系統發現您的帳號有異常登入活動，請選擇接下來的操作。"

        // XML 需要手動建立 Layout 結構
        val row = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            gravity = Gravity.CENTER_VERTICAL
        }

        val btnIgnore = MaterialButton(requireContext(), null, com.google.android.material.R.attr.borderlessButtonStyle).apply {
            text = "忽略提示"
            setTextColor(Color.GRAY)
        }

        val spacer = View(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(0, 0, 1f)
        }

        val btnLogs = MaterialButton(requireContext(), null, com.google.android.material.R.attr.materialButtonOutlinedStyle).apply {
            text = "查看日誌"
        }

        val btnChange = MaterialButton(requireContext()).apply {
            text = "立刻修改密碼"
            backgroundTintList = ColorStateList.valueOf(Color.BLUE)
            setTextColor(Color.WHITE)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                marginStart = 8.px
            }
        }

        row.addView(btnIgnore)
        row.addView(spacer)
        row.addView(btnLogs)
        row.addView(btnChange)
        
        card.ctaContainer.addView(row)
    }

    private fun setupZebraActionCard() {
        val card = binding.cardZebraAction
        card.tvTitle.text = "偵測到安全性風險"
        card.tvDescription.text = "系統發現您的帳號有異常登入活動，請選擇接下來的操作。"

        val row = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                20.px
            )
        }

        for (i in 0 until 20) {
            val box = View(requireContext()).apply {
                setBackgroundColor(if (i % 2 == 0) Color.BLACK else Color.WHITE)
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
            }
            row.addView(box)
        }
        card.ctaContainer.addView(row)
    }

    private fun setupZebraAnimCard() {
        val card = binding.cardZebraAnim
        card.tvTitle.text = "無限捲動斑馬線"
        card.tvDescription.text = "這是一個利用 XML 手動管理動畫與捲動元件的範例。"

        val hsv = HorizontalScrollView(requireContext()).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                20.px
            )
            isClickable = false
            isFocusable = false
            getChildAt(0)?.let { it.isClickable = false } // Disable scroll by touch
        }

        val row = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.HORIZONTAL
        }

        for (i in 0 until 50) {
            val box = View(requireContext()).apply {
                setBackgroundColor(if (i % 2 == 0) Color.BLACK else Color.WHITE)
                layoutParams = LinearLayout.LayoutParams(20.px, 20.px)
            }
            row.addView(box)
        }
        hsv.addView(row)
        card.ctaContainer.addView(hsv)

        // XML 必須手動建立動畫器
        val animator = ValueAnimator.ofInt(0, 1000).apply {
            duration = 2000
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener { animation ->
                val value = animation.animatedValue as Int
                // 這裡需要手動計算捲動位置，非常麻煩
                hsv.scrollTo((value * (50 * 20.px - resources.displayMetrics.widthPixels) / 1000).coerceAtLeast(0), 0)
            }
        }
        hsv.post { animator.start() }
    }

    // 擴充屬性：將 dp 轉換為 px
    private val Int.px: Int
        get() = (this * resources.displayMetrics.density).toInt()
}
