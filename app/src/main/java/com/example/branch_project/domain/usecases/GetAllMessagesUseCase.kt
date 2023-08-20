package com.example.branch_project.domain.usecases

import com.example.branch_project.domain.model.MessageItem
import com.example.branch_project.domain.repository.BranchRepository
import com.example.branch_project.util.AgentPreferences
import com.example.branch_project.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetAllMessagesUseCase @Inject constructor(
    private val repository: BranchRepository,
    private val agentPreferences: AgentPreferences
) {
    operator fun invoke(): Flow<Resource<Map<Int, List<MessageItem>>>> = flow {
        emit(Resource.Loading())
        val authToken = agentPreferences.getAuthToken()
        val response = authToken?.let {
            repository.getAllMessages(authToken)
        }
        response?.let {
            emit(response)
        }
    }
}