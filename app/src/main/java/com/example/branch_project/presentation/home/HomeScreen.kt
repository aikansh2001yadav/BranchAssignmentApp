package com.example.branch_project.presentation.home

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.branch_project.presentation.commons.ErrorState
import com.example.branch_project.presentation.navigation.Screen
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current
    val swipeRefreshState = rememberSwipeRefreshState(false)

    SwipeRefresh(state = swipeRefreshState, onRefresh = {
        viewModel.fetchAllMessages()
    }) {
        Box(contentAlignment = Alignment.Center) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.messageItemMap.toSortedMap().toList()) { item ->
                    val threadId = item.first
                    val message = item.second.sortedBy {
                        it.timestamp
                    }.last()
                    (message.agentId ?: message.userId)?.let {
                        ThreadItem(
                            body = message.body,
                            timestamp = message.timestamp,
                            id = it,
                            modifier = Modifier
                                .clickable(enabled = true) {
                                    navController.navigate(Screen.ChatScreen.route + "/" + "$threadId")
                                }
                                .padding(vertical = 5.dp)
                        )
                    }
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator()
            }
            if (state.error != null) {
                ErrorState(modifier = Modifier.fillMaxSize())
                Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(key1 = null) {
        viewModel.fetchAllMessages()
    }
}