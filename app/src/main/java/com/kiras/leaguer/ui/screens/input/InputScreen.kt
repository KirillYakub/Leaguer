package com.kiras.leaguer.ui.screens.input

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kiras.leaguer.R
import com.kiras.leaguer.ui.common.PasswordComponent
import com.kiras.leaguer.ui.screens.input.components.InputState

@Composable
fun InputScreen(
    state: InputState,
    nextScreen: () -> Unit,
    onPasswordChange: (String) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if(state.isLoading)
            CircularProgressIndicator(strokeWidth = 5.dp)
        else {
            LaunchedEffect(state.isPasswordEnabled, state.inputPassword) {
                if(!state.isPasswordEnabled || state.inputPassword == state.correctPassword)
                    nextScreen()
            }
            if(state.isPasswordEnabled) {
                Column {
                    Image(
                        painter = painterResource(R.drawable.hero_face),
                        contentDescription = "Hero screen input face",
                        modifier = Modifier
                            .fillMaxHeight(0.4f)
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Enter password",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    PasswordComponent(
                        password = state.inputPassword,
                        onPasswordChange = onPasswordChange
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    if (!state.isInputPasswordCorrect) {
                        Text(
                            text = "Wrong password.",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }
}