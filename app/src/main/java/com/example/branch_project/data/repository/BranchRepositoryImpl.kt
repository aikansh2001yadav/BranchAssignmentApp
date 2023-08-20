package com.example.branch_project.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.branch_project.data.model.ErrorResponse
import com.example.branch_project.data.model.LoginRequest
import com.example.branch_project.data.model.MessageRequest
import com.example.branch_project.data.remote.BranchService
import com.example.branch_project.domain.model.MessageItem
import com.example.branch_project.domain.repository.BranchRepository
import com.example.branch_project.util.Resource
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class BranchRepositoryImpl @Inject constructor(
    private val branchService: BranchService
) : BranchRepository {
    override var messageItemMap: Map<Int, List<MessageItem>>? = null

    override suspend fun login(email: String, password: String): Resource<String> {
        return try {
            val response =
                branchService.login(LoginRequest(username = email, password = password))
            if (response.isSuccessful) {
                Resource.Success(response.body()?.authToken)
            } else {
                try {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    val errorResponse = jObjError.getString("error")
                    Resource.Error(errorResponse ?: "Something went wrong")
                } catch (e: JSONException) {
                    Resource.Error(e.message ?: "Something went wrong")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error("Something went wrong")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getAllMessages(authToken: String): Resource<Map<Int, List<MessageItem>>> {
        return try {
            val response = branchService.getAllMessages(authToken = authToken)
            if (response.isSuccessful) {
                val result = response.body()?.map {
                    it.toDomainEntity()
                }?.groupBy { it.threadId }
                messageItemMap = result ?: emptyMap()
                Resource.Success(result)
            } else {
                try {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    val errorResponse = jObjError.getString("error")
                    Resource.Error(errorResponse ?: "Something went wrong")
                } catch (e: JSONException) {
                    Resource.Error(e.message ?: "Something went wrong")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error("Something went wrong")
        }
    }

    override suspend fun getMessageItemMap(threadId: Int): Resource<Map<Int, List<MessageItem>>> {
        val itemMap = messageItemMap?.filterKeys {
            it == threadId
        } ?: emptyMap()
        return Resource.Success(itemMap)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun sendMessage(
        authToken: String,
        threadId: Int,
        body: String
    ): Resource<MessageItem> {
        return try {
            val response =
                branchService.sendMessage(
                    authToken = authToken,
                    MessageRequest(threadId = threadId, body = body)
                )
            if (response.isSuccessful) {
                Resource.Success(response.body()?.toDomainEntity())
            } else {
                try {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    val errorResponse = jObjError.getString("error")
                    Resource.Error(errorResponse ?: "Something went wrong")
                } catch (e: JSONException) {
                    Resource.Error(e.message ?: "Something went wrong")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error("Something went wrong")
        }
    }
}