package com.example.branch_project.data.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.branch_project.domain.model.MessageItem
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

data class MessageDto(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("thread_id")
    val threadId: Int,
    @field:SerializedName("user_id")
    val userId: String?,
    @field:SerializedName("agent_id")
    val agentId: String?,
    @field:SerializedName("body")
    val body: String,
    @field:SerializedName("timestamp")
    val timestamp: String
) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun toDomainEntity(): MessageItem {
        val pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
        val localDateTime = LocalDateTime.parse(timestamp, formatter)
        return MessageItem(
            id = id,
            threadId = threadId,
            userId = userId,
            agentId = agentId,
            body = body,
            timestamp = localDateTime,
        )
    }
}