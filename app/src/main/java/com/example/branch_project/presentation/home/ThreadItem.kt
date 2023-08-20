package com.example.branch_project.presentation.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ThreadItem(
    body: String,
    timestamp: LocalDateTime,
    id: String,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .border(2.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(
            text = timestamp.format(DateTimeFormatter.ofPattern("MMM d yyy, h:mm a")),
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(text = "ID $id")
        Text(text = body, modifier = Modifier.padding(bottom = 8.dp))
    }
}