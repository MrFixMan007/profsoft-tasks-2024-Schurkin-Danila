package com.example.network.dto.authenticated

data class CourseResponse(
    val status: Int,
    val message: String?,
    val data: CourseDto?
)