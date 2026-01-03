package com.user.music.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.user.music.player.PlayerViewModel
import com.user.music.ui.AudioRoute
import com.user.music.ui.HomeRoute
import com.user.music.ui.home.HomeViewModel

@Composable
fun AppNavGraph(
    homeViewModel: HomeViewModel,
    audioViewModel: PlayerViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Home.path
    ) {

        composable(Route.Home.path) {
            HomeRoute(
                viewModel = homeViewModel,
                navController = navController,
                audioViewModel = audioViewModel
            )
        }

        composable(
            route = Route.Audio.path,
            arguments = listOf(
                navArgument("id") { type = NavType.StringType }
            )
        ) {
            AudioRoute(
                viewModel = audioViewModel,
                trackId = it.arguments!!.getString("id")!!
            )
        }

    }
}
