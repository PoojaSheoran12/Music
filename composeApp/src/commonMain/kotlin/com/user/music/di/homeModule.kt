package com.user.music.di


import com.user.music.data.repository.HomeRepositoryImpl
import com.user.music.domain.repository.HomeRepository
import com.user.music.ui.home.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable.get
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module


val homeModule = module {


    single<HomeRepository> { HomeRepositoryImpl(
        get(),
        database = get()
    ) }

    factory {
        HomeViewModel(
            repository = get<HomeRepository>(),
            scope = get()
        )
    }
}
