package com.example.branch_project.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
    @field:SerializedName("auth_token")
    val authToken: String,
    @field:SerializedName("error")
    val error: String? = null
)