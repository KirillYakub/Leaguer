package com.kiras.leaguer.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PasswordComponent(
    password: String,
    onPasswordChange: (String) -> Unit
) {
    BasicTextField(
        value = password,
        onValueChange = { otpValue ->
            if (otpValue.length <= 4 && otpValue.all { it.isDigit() }) {
                onPasswordChange(otpValue)
            }
        },
        decorationBox = {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                repeat(4) { index ->
                    val number = when {
                        index >= password.length -> ""
                        else -> password[index].toString()
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = number,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Box(
                            modifier = Modifier
                                .width(40.dp)
                                .height(2.dp)
                                .background(MaterialTheme.colorScheme.onBackground)
                        )
                    }
                }
            }
        }
    )
}