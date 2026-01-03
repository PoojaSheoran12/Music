package com.user.music.di


import com.user.music.data.repository.HomeRepositoryImpl
import com.user.music.domain.repository.HomeRepository
import com.user.music.presentation.home.HomeViewModel
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
