package com.kiras.leaguer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.kiras.leaguer.ui.screens.champion_details.ChampionDetailsScreen
import com.kiras.leaguer.ui.screens.champion_details.components.ChampionDetailsViewModel
import com.kiras.leaguer.ui.screens.champions_list.ChampionsListScreen
import com.kiras.leaguer.ui.screens.champions_list.components.ChampionsListViewModel
import com.kiras.leaguer.ui.screens.input.InputScreen
import com.kiras.leaguer.ui.screens.input.components.InputViewModel
import com.kiras.leaguer.ui.screens.settings.SettingsScreen
import com.kiras.leaguer.ui.screens.settings.components.SettingsViewModel
import com.kiras.leaguer.ui.screens.settings.components.state.SettingsEvent
import com.kiras.leaguer.ui.theme.LeaguerTheme
import com.kiras.leaguer.util.ChampionsDetails
import com.kiras.leaguer.util.ChampionsList
import com.kiras.leaguer.util.Constants.NOTIFICATIONS_ARG
import com.kiras.leaguer.util.Constants.NOTIFICATIONS_DEEPLINK_URI
import com.kiras.leaguer.util.Input
import com.kiras.leaguer.util.Settings
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LeaguerTheme {
                UIConfiguration()
                Scaffold { padding ->
                    val controller = rememberNavController()
                    NavHost(
                        modifier = Modifier.padding(padding),
                        navController = controller,
                        startDestination = Input
                    ) {
                        composable<Input> {
                            val model: InputViewModel = hiltViewModel()
                            val state by model.state.collectAsStateWithLifecycle()
                            InputScreen(
                                state = state,
                                nextScreen = {
                                    controller.navigate(ChampionsList) {
                                        launchSingleTop = true
                                        popUpTo<Input> {
                                            inclusive = true
                                        }
                                    }
                                },
                                onPasswordChange = model::onPasswordChange
                            )
                        }
                        composable<Settings> {
                            val model: SettingsViewModel = hiltViewModel()
                            SettingsScreen(
                                state = model.settingsState,
                                onNotificationChange = { isEnable ->
                                    model.onEvent(SettingsEvent.NotificationsChange(isEnable))
                                },
                                onPasswordChange = { isEnable, password ->
                                    model.onEvent(SettingsEvent.PasswordChange(isEnable, password))
                                }
                            )
                        }
                        composable<ChampionsList> {
                            val model: ChampionsListViewModel = hiltViewModel()
                            val state by model.state.collectAsStateWithLifecycle()
                            ChampionsListScreen(
                                state = state,
                                onSearchValueChange = model::onSearchTextChange,
                                onRetry = model::getAllChampions,
                                onChampionClick = { name ->
                                    controller.navigate(ChampionsDetails(name = name)) {
                                        launchSingleTop = true
                                    }
                                },
                                onSettingsClick = {
                                    controller.navigate(Settings) {
                                        launchSingleTop = true
                                    }
                                }
                            )
                        }
                        composable<ChampionsDetails>(
                            deepLinks = listOf(
                                navDeepLink {
                                    uriPattern = "$NOTIFICATIONS_DEEPLINK_URI/" +
                                            "$NOTIFICATIONS_ARG={$NOTIFICATIONS_ARG}"
                                }
                            )
                        ) {
                            val model: ChampionDetailsViewModel = hiltViewModel()
                            model.state?.let { champion ->
                                ChampionDetailsScreen(
                                    state = champion,
                                    onRetry = model::getChampionByName
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UIConfiguration() {
    val activity = LocalActivity.current
    val defaultSystemBarsColor = MaterialTheme.colorScheme.background
    SideEffect {
        activity?.apply {
            window?.statusBarColor = defaultSystemBarsColor.toArgb()
            window?.navigationBarColor = defaultSystemBarsColor.toArgb()
        }
    }
}