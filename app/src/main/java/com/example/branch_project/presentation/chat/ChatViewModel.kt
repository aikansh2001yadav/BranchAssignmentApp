package com.example.branch_project.presentation.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.branch_project.domain.model.MessageItem
import com.example.branch_project.domain.usecases.GetMessageItemMapUseCase
import com.example.branch_project.domain.usecases.SendMessageUseCase
import com.example.branch_project.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val sendMessageUseCase: SendMessageUseCase,
    getMessageItemMapUseCase: GetMessageItemMapUseCase
) : ViewModel() {

    val mutableState: MutableLiveData<ChatState> = MutableLiveData(ChatState())
    val state: LiveData<ChatState> = mutableState

    init {
        mutableState.value = mutableState.value?.copy(messageItemMap = getMessageItemMapUseCase())
    }

    fun sendMessage(threadId: Int, body: String) {
        sendMessageUseCase(threadId = threadId, body = body).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let {
                        mutableState.value?.let {
                            val updatedMap = it.messageItemMap +
                                    (threadId to it.messageItemMap[threadId]?.plus(result.data))
                            mutableState.value = it.copy(
                                messageItemMap = updatedMap as Map<Int, List<MessageItem>>,
                                text = "",
                                isBeingSent = false,
                                error = null
                            )
                        }
                    }
                }

                is Resource.Loading -> {
                    mutableState.value = mutableState.value?.copy(isBeingSent = true, error = null)
                }

                is Resource.Error -> {
                    mutableState.value =
                        mutableState.value?.copy(error = result.message, isBeingSent = false)
                }
            }
        }.launchIn(viewModelScope)
    }
}