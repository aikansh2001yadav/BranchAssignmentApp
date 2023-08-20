package com.example.branch_project.domain.repository

import com.example.branch_project.domain.model.MessageItem
import com.example.branch_project.util.Resource

interface BranchRepository {

    var messageItemMap: Map<Int, List<MessageItem>>?

    suspend fun login(
        email: String,
        password: String
    ): Resource<String>

    suspend fun getAllMessages(
        authToken: String
    ): Resource<Map<Int, List<MessageItem>>>

    suspend fun getMessageItemMap(
        threadId: Int
    ) : Resource<Map<Int, List<MessageItem>>>

    suspend fun sendMessage(
        authToken: String,
        threadId: Int,
        body: String,
    ): Resource<MessageItem>
}