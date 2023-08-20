package com.example.branch_project.presentation.chat

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatScreen(
    navController: NavHostController,
    threadId: Int,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state.observeAsState()
    val messageList = state.value?.let {
        it.messageItemMap[threadId]?.sortedBy { item ->
            item.timestamp
        }
    } ?: emptyList()

    Column(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(top = 8.dp, start = 8.dp, end = 8.dp)
        ) {
            items(messageList) { message ->
                MessageItem(messageItem = message)
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            val bringIntoViewRequester = remember { BringIntoViewRequester() }
            val coroutineScope = rememberCoroutineScope()
            OutlinedTextField(
                value = state.value?.text ?: "",
                onValueChange = {
                    viewModel.mutableState.value = state.value?.copy(text = it)
                },
                placeholder = {
                    Text(
                        text = "Enter message",
                        modifier = Modifier
                    )
                },
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .bringIntoViewRequester(bringIntoViewRequester)
                    .onFocusEvent { focusState ->
                        if (focusState.isFocused) {
                            coroutineScope.launch {
                                bringIntoViewRequester.bringIntoView()
                            }
                        }
                    },
                trailingIcon = {
                    if (state.value?.isBeingSent == true) {
                        CircularProgressIndicator(modifier = Modifier.size(25.dp))
                    } else {
                        IconButton(
                            onClick = {
                                viewModel.sendMessage(
                                    threadId = threadId,
                                    body = state.value?.text ?: ""
                                )
                            }) {
                            Icon(Icons.Filled.Send, contentDescription = null)
                        }

                    }
                }
            )
        }
    }

    if (state.value?.error != null) {
        Toast.makeText(context, state.value?.error, Toast.LENGTH_SHORT).show()
    }
}