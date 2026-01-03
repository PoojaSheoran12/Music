package com.user.music.di

import com.user.music.data.network.ApiClient
import com.user.music.data.network.ApiService
import com.user.music.data.repository.HomeRepository
import com.user.music.data.repository.HomeRepositoryImpl
import com.user.music.ui.home.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val homeModule = module {

    factory { CoroutineScope(SupervisorJob() + Dispatchers.Main) }

    single<HomeRepository> { HomeRepositoryImpl(get()) }

    factory {
        HomeViewModel(
            repository = get(),
            scope = get()
        )
    }
}
