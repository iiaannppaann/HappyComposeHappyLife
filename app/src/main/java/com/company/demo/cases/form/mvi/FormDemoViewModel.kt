package com.company.demo.cases.form.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FormDemoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(FormDemoUiState())
    val uiState: StateFlow<FormDemoUiState> = _uiState.asStateFlow()

    // 更新特定欄位的輸入值
    fun updateField(id: String, newValue: String) {
        _uiState.update { currentState ->
            val updatedFields = currentState.fields.map { field ->
                if (field.id == id) field.copy(value = newValue) else field
            }
            currentState.copy(fields = updatedFields, submitResult = "")
        }
    }

    // 動態新增一個輸入欄位
    fun addField() {
        _uiState.update { currentState ->
            val nextIndex = currentState.fields.size + 1
            val newField = FormField("field_$nextIndex", "動態欄位 $nextIndex")
            currentState.copy(fields = currentState.fields + newField, submitResult = "")
        }
    }

    // 動態移除最後一個輸入欄位
    fun removeField() {
        _uiState.update { currentState ->
            if (currentState.fields.isNotEmpty()) {
                currentState.copy(fields = currentState.fields.dropLast(1), submitResult = "")
            } else {
                currentState
            }
        }
    }

    // 提交表單，彙整所有欄位的值
    fun submitForm() {
        _uiState.update { currentState ->
            val summary = currentState.fields.joinToString(", ") { "${it.label}: ${it.value}" }
            currentState.copy(submitResult = "已送出: $summary")
        }
    }
}
