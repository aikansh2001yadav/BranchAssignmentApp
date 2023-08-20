package com.example.branch_project.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.branch_project.domain.repository.BranchRepository
import com.example.branch_project.domain.usecases.LoginUseCase
import com.example.branch_project.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    var state by mutableStateOf(LoginState())

    fun login(
        email: String = state.email,
        password: String = state.password,
        onLoginSuccess: () -> Unit,
        onError : (message : String?) -> Unit
    ) {
        loginUseCase(email, password).onEach { result ->
            when(result){
                is Resource.Loading -> {
                    state = state.copy(isLoggingIn = true)
                }
                is Resource.Error -> {
                    state = state.copy(isLoggingIn = false)
                    onError(result.message)
                }
                is Resource.Success -> {
                    state = state.copy(isLoggingIn = false)
                    result.data?.let{
                        onLoginSuccess()
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}