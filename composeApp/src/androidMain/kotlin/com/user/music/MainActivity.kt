package com.user.music

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.user.music.audio.AudioViewModel
import com.user.music.navigation.AppNavGraph
import com.user.music.ui.HomeViewModel
import org.koin.mp.KoinPlatform.getKoin

class MainActivity : ComponentActivity() {
    private val audioViewModel: AudioViewModel by lazy {
        getKoin().get<AudioViewModel>()
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
//
//@Preview
//@Composable
//fun AppAndroidPreview() {
//    App()
//}