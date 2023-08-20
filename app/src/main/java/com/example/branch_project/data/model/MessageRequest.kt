package com.example.branch_project.data.model

import com.google.gson.annotations.SerializedName

data class MessageRequest(
    @field:SerializedName("thread_id")
    val threadId: Int,
    @field:SerializedName("body")
    val body: String,
)