package com.example.branch_project.domain.usecases

import com.example.branch_project.domain.model.MessageItem
import com.example.branch_project.domain.repository.BranchRepository
import com.example.branch_project.util.AgentPreferences
import com.example.branch_project.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val repository: BranchRepository,
    private val agentPreferences: AgentPreferences
) {
    operator fun invoke(threadId: Int, body: String): Flow<Resource<MessageItem>> = flow {
        emit(Resource.Loading())
        val authToken = agentPreferences.getAuthToken()
        val response = authToken?.let {
            repository.sendMessage(
                authToken = authToken,
                threadId = threadId, body = body
            )
        }
        response?.let {
            emit(response)
        }
    }
}