package com.example.branch_project.presentation.chat

import com.example.branch_project.domain.model.MessageItem

data class ChatState(
    val messageItemMap: Map<Int, List<MessageItem>> = emptyMap(),
    val text: String = "",
    val messageItemSent: MessageItem? = null,
    val isBeingSent: Boolean = false,
    val error : String? = null
)