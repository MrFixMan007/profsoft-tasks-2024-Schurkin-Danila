package com.example.common.domain.model.authenticated

data class Course(
    val isSuccess: Boolean,
    val id: String,
    val title: String,
    val description: String,
    val tags: List<String>? = null,
    val text: List<Text>? = null
)