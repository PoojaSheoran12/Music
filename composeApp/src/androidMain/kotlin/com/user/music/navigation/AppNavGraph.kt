package com.user.music.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.user.music.audio.AudioScreen
import com.user.music.audio.AudioViewModel
import com.user.music.ui.AudioRoute
import com.user.music.ui.HomeRoute
import com.user.music.ui.HomeScreen
import com.user.music.ui.HomeViewModel

@Composable
fun AppNavGraph(
    homeViewModel: HomeViewModel,
    audioViewModel: AudioViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Home.path
    ) {

        composable(Route.Home.path) {
            HomeRoute(
                viewModel = homeViewModel,
                navController = navController
            )
        }

        composable(
            Route.Audio.path,
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
                navArgument("url") { type = NavType.StringType }
            )
        ) {
            AudioRoute(
                viewModel = audioViewModel,
                trackId = it.arguments!!.getString("id")!!,
                audioUrl = it.arguments!!.getString("url")!!
            )
        }
    }
}
