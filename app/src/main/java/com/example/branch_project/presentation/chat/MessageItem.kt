package com.example.branch_project.presentation.chat


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.unit.dp
import com.example.branch_project.domain.model.MessageItem
import com.example.branch_project.ui.theme.LightBlue
import com.example.branch_project.ui.theme.LightGreen
import com.example.branch_project.ui.theme.LightYellow
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MessageItem(messageItem: MessageItem) {
    val isSelf: Boolean = (messageItem.agentId != null)
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isSelf) Alignment.End else Alignment.Start
    ) {

        Text(
            text = (if (isSelf) "Agent Id ${messageItem.agentId}" else "User Id ${messageItem.userId!!}")
        )
        Spacer(modifier = Modifier.height(5.dp))
        BoxWithConstraints {
            Box(
                modifier = Modifier
                    .background(
                        color = if (isSelf) LightBlue else LightGray,
                        shape = if (isSelf) RoundedCornerShape(8.dp, 0.dp, 8.dp, 8.dp)
                        else RoundedCornerShape(0.dp, 8.dp, 8.dp, 8.dp)
                    )
                    .widthIn(max = maxWidth * 0.8f, min = 0.dp)
                    .padding(horizontal = 10.dp, vertical = 5.dp),
            ) {
                Column() {
                    Text(
                        text = messageItem.timestamp.format(DateTimeFormatter.ofPattern("MMM d yyy, h:mm a")),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(text = messageItem.body)
                }

            }
        }


    }

}