package com.company.demo.cases.form.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FormDemoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(FormDemoUiState())
    val uiState: StateFlow<FormDemoUiState> = _uiState.asStateFlow()

    fun updateField(id: String, newValue: String) {
        _uiState.update { currentState ->
            val updatedFields = currentState.fields.map { field ->
                if (field.id == id) field.copy(value = newValue) else field
            }
            currentState.copy(fields = updatedFields, submitResult = "")
        }
    }

    fun submitForm() {
        _uiState.update { currentState ->
            val summary = currentState.fields.joinToString(", ") { "${it.label}: ${it.value}" }
            currentState.copy(submitResult = "已送出: $summary")
        }
    }
}
