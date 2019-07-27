package com.example.rappitest.di

import com.example.rappitest.repository.TmdbRepositoryApi
import com.example.rappitest.repository.TmdbRepositoryImpl
import com.example.rappitest.repository.remote.TmdbServiceApi
import com.example.rappitest.repository.remote.TmdbServiceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RappiTestApplicationModule {

    @Binds
    abstract fun bindRepository(repository: TmdbRepositoryImpl) : TmdbRepositoryApi

    @Binds
    abstract fun bindService(service: TmdbServiceImpl) : TmdbServiceApi
}