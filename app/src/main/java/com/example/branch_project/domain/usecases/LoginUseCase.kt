package com.example.branch_project.domain.usecases

import com.example.branch_project.data.model.LoginRequest
import com.example.branch_project.domain.repository.BranchRepository
import com.example.branch_project.util.AgentPreferences
import com.example.branch_project.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: BranchRepository,
    private val agentPreferences: AgentPreferences
) {
    operator fun invoke(email: String, password: String): Flow<Resource<String>> = flow {

        emit(Resource.Loading())

        val response = repository.login(email = email, password = password)
        if (response is Resource.Success) {
            response.data?.let {
                agentPreferences.saveAuthToken(it)
                emit(Resource.Success(data = it))
            }
        } else {
            emit(response)
        }
    }
}