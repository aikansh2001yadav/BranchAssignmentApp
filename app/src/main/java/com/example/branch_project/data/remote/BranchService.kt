package com.example.branch_project.data.remote

import com.example.branch_project.data.model.LoginRequest
import com.example.branch_project.data.model.LoginResponseDto
import com.example.branch_project.data.model.MessageDto
import com.example.branch_project.data.model.MessageRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface BranchService {

    @POST("api/login")
    suspend fun login(
        @Body loginRequest: LoginRequest,
    ): Response<LoginResponseDto>

    @GET("api/messages")
    suspend fun getAllMessages(
        @Header("X-Branch-Auth-Token") authToken: String,
    ): Response<List<MessageDto>>

    @POST("api/messages")
    suspend fun sendMessage(
        @Header("X-Branch-Auth-Token") authToken: String,
        @Body messageRequest: MessageRequest
    ): Response<MessageDto>

    companion object {
        const val BASE_URL = "https://android-messaging.branch.co/"
    }
}