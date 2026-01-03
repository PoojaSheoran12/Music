package com.user.music

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.user.music.player.PlayerViewModel
import com.user.music.navigation.AppNavGraph
import com.user.music.ui.home.HomeViewModel
import org.koin.mp.KoinPlatform.getKoin

class MainActivity : ComponentActivity() {
    private val audioViewModel: PlayerViewModel by lazy {
        getKoin().get<PlayerViewModel>()
    }
    private val homeViewModel: HomeViewModel by lazy {
        getKoin().get<HomeViewModel>()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            AppNavGraph(
                homeViewModel = homeViewModel,
                audioViewModel = audioViewModel
            )
        }
    }
}
