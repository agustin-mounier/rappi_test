package com.example.rappitest.di

import com.example.rappitest.repository.TmdbRepositoryApi
import com.example.rappitest.repository.remote.TmdbServiceApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RappiTestApplicationModule::class])
interface RappiTestApplicationComponent {

    fun injectRepository(): TmdbRepositoryApi

    fun injectService(): TmdbServiceApi
}