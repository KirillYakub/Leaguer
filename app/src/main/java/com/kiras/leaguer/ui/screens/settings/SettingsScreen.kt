package com.kiras.leaguer.ui.screens.settings

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kiras.leaguer.ui.common.PasswordComponent
import com.kiras.leaguer.ui.screens.settings.components.state.SettingsState
import com.kiras.leaguer.ui.theme.White

@Composable
fun SettingsScreen(
    state: SettingsState,
    onNotificationChange: (Boolean) -> Unit,
    onPasswordChange: (Boolean, String) -> Unit
) {
    val notificationsPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isAccepted ->
            if(isAccepted)
                onNotificationChange(!state.isNotificationEnabled)
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 10.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Notifications",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Switch(
                    checked = state.isNotificationEnabled,
                    onCheckedChange = { checked ->
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                            notificationsPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
                        else
                            onNotificationChange(checked)
                    },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = MaterialTheme.colorScheme.primary,
                        checkedThumbColor = White,
                        checkedBorderColor = MaterialTheme.colorScheme.background
                    )
                )
            }
            Text(
                text = "Get notifications about your favorite heroes so you don't forget about their stories and skills.",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Password",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Switch(
                    checked = state.isPasswordEnabled,
                    onCheckedChange = { checked ->
                        onPasswordChange(checked, state.password)
                    },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = MaterialTheme.colorScheme.primary,
                        checkedThumbColor = White,
                        checkedBorderColor = MaterialTheme.colorScheme.background
                    )
                )
            }
            Text(
                text = "Enable password to keep your champions data safe.",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
            Spacer(modifier = Modifier.height(14.dp))
            AnimatedVisibility(visible = state.isPasswordEnabled) {
                PasswordComponent(
                    password = state.password,
                    onPasswordChange = { password ->
                        onPasswordChange(state.isPasswordEnabled, password)
                    }
                )
            }
            if(!state.isPasswordCorrect) {
                Text(
                    text = "Password must contain 4 characters to save.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}