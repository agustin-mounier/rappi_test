package com.example.rappitest.dagger

import android.app.Application
import com.example.rappitest.RappiTestApplication
import com.example.rappitest.repository.TmdbRepositoryApi
import com.example.rappitest.repository.TmdbRepositoryImpl
import com.example.rappitest.repository.local.TmdbDao
import com.example.rappitest.repository.local.TmdbDaoImpl
import com.example.rappitest.repository.remote.TmdbServiceApi
import com.example.rappitest.repository.remote.TmdbServiceImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindApplication(application: RappiTestApplication): Application

    @Binds
    abstract fun bindRepository(repository: TmdbRepositoryImpl): TmdbRepositoryApi

    @Binds
    abstract fun bindService(service: TmdbServiceImpl): TmdbServiceApi

    @Binds
    abstract fun bindDao(dao: TmdbDaoImpl): TmdbDao
}