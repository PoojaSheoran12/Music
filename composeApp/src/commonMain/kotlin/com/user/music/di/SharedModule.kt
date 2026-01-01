package com.user.music.di

import com.user.music.network.ApiClient
import com.user.music.network.ApiService
import com.user.music.repository.HomeRepository
import com.user.music.repository.HomeRepositoryImpl
import com.user.music.ui.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val sharedModule = module {

    factory { CoroutineScope(SupervisorJob() + Dispatchers.Main) }

    single { ApiClient().client }
    single { ApiService(get()) }

    single<HomeRepository> { HomeRepositoryImpl(get()) }

    factory {
        HomeViewModel(
            repository = get(),
            scope = get()
        )
    }
}
