package com.example.branch_project.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.branch_project.domain.usecases.GetAllMessagesUseCase
import com.example.branch_project.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllMessagesUseCase: GetAllMessagesUseCase
) : ViewModel() {

    var state by mutableStateOf(HomeState())

    fun fetchAllMessages() {
        state = state.copy(messageItemMap = emptyMap())
        getAllMessagesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let {
                        state = state.copy(messageItemMap = it, isLoading = false, error = null)
                    }
                }

                is Resource.Loading -> {
                    state = state.copy(isLoading = true, error = null)
                }

                is Resource.Error -> {
                    state = state.copy(error = result.message ?: "Something went wrong", isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }
}