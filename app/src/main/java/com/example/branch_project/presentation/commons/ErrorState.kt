package com.example.branch_project.presentation.commons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.branch_project.R
import java.lang.Exception

@Composable
fun ErrorState(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        Image(
            painter = painterResource(
                R.drawable.ic_internet_error
            ),
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = stringResource(R.string.internet_error),
            color = Color.Red
        )
        Text(
            text =
                stringResource(R.string.internet_error_info),
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(100.dp))
    }
}