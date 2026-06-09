package com.company.demo.cases.form.xml

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.company.demo.cases.form.mvi.FormDemoViewModel
import com.company.demo.cases.form.mvi.FormField
import com.company.demo.databinding.FragmentFormDemoXmlBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FormDemoXmlFragment : Fragment() {

    private lateinit var binding: FragmentFormDemoXmlBinding
    private val viewModel: FormDemoViewModel by viewModels()

    // 用於保存目前動態欄位中 EditText 的 TextWatcher，以避免重複綁定或遞迴更新
    private val textWatchers = mutableMapOf<String, TextWatcher>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFormDemoXmlBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 設定控制鈕點擊事件
        binding.btnAddField.setOnClickListener {
            viewModel.addField()
        }

        binding.btnRemoveField.setOnClickListener {
            viewModel.removeField()
        }

        binding.btnSubmit.setOnClickListener {
            viewModel.submitForm()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                syncDynamicFields(state.fields)
                binding.tvSubmitResult.text = state.submitResult
            }
        }
    }

    // 核心對比展示：手動動態增減與更新 View 樹的繁瑣過程
    private fun syncDynamicFields(fields: List<FormField>) {
        val container = binding.llDynamicFieldsContainer
        val currentChildCount = container.childCount
        val targetSize = fields.size

        // 1. 如果現有子視圖數量大於目標數量，需要手動呼叫 removeViews 移除多餘的 View
        if (currentChildCount > targetSize) {
            container.removeViews(targetSize, currentChildCount - targetSize)
        }

        // 2. 遍歷並同步每一個欄位元件
        for (i in 0 until targetSize) {
            val field = fields[i]
            val fieldLayout: LinearLayout

            if (i < currentChildCount) {
                // 已存在的 View，直接重複使用
                fieldLayout = container.getChildAt(i) as LinearLayout
            } else {
                // 3. 不夠時需要手動透過 addView 動態建立並加載 View
                fieldLayout = createFieldLayout(field.id)
                container.addView(fieldLayout)
            }

            // 取得子 View
            val tvLabel = fieldLayout.getChildAt(0) as TextView
            val etInput = fieldLayout.getChildAt(1) as EditText

            // 更新 Label 文字
            tvLabel.text = field.label

            // 4. 管理 TextWatcher 避免 setText() 造成無限遞迴或游標跳動
            val oldWatcher = textWatchers[field.id]
            if (oldWatcher != null) {
                etInput.removeTextChangedListener(oldWatcher)
            }

            // 更新 EditText 的值 (僅在內容不一致時更新，以避免游標重置)
            val currentText = etInput.text.toString()
            if (currentText != field.value) {
                etInput.setText(field.value)
                etInput.setSelection(field.value.length)
            }

            // 重新建立並綁定 TextWatcher，捕捉使用者的動態輸入並傳回 ViewModel
            val newWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.updateField(field.id, s?.toString() ?: "")
                }
                override fun afterTextChanged(s: Editable?) {}
            }
            etInput.addTextChangedListener(newWatcher)
            textWatchers[field.id] = newWatcher
        }
    }

    // 程式化動態建立與設定佈局結構 (相當於 Compose Modifier + Layout 宣告)
    private fun createFieldLayout(fieldId: String): LinearLayout {
        return LinearLayout(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.VERTICAL
            tag = fieldId

            // 建立 Label TextView
            val tvLabel = TextView(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }
            addView(tvLabel)

            // 建立 Input EditText
            val etInput = EditText(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    bottomMargin = (12 * resources.displayMetrics.density).toInt()
                }
                importantForAutofill = View.IMPORTANT_FOR_AUTOFILL_NO
                inputType = android.text.InputType.TYPE_CLASS_TEXT
            }
            addView(etInput)
        }
    }
}
