package com.example.branch_project.presentation.home

import com.example.branch_project.domain.model.MessageItem

data class HomeState(
    val messageItemMap: Map<Int, List<MessageItem>> = emptyMap(),
    val isLoading: Boolean = false,
    val isRefreshing : Boolean = false,
    val error : String? = null
)