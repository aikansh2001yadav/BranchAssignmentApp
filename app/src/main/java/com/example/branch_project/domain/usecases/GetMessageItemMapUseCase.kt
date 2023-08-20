package com.example.branch_project.domain.usecases

import com.example.branch_project.domain.model.MessageItem
import com.example.branch_project.domain.repository.BranchRepository
import com.example.branch_project.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMessageItemMapUseCase @Inject constructor(
    private val repository: BranchRepository
) {
    operator fun invoke(): Map<Int, List<MessageItem>> = repository.messageItemMap ?: emptyMap()
}