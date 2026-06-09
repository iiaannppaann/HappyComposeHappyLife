package com.company.demo.cases.form.mvi

data class FormField(
    val id: String,
    val label: String,
    val value: String = ""
)

data class FormDemoUiState(
    val fields: List<FormField> = listOf(
        FormField("name", "姓名"),
        FormField("email", "信箱")
    ),
    val submitResult: String = ""
)
