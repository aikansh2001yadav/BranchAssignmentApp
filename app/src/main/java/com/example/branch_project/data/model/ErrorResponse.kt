package com.example.branch_project.data.model

data class ErrorResponse(
    var message: String?,
    var exception: Exception? = null
)