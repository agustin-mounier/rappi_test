package com.example.rappitest.dagger

import com.example.rappitest.RappiTestApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    RealmModule::class,
    ViewModelsModule::class,
    ActivitiesModule::class,
    AndroidInjectionModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: RappiTestApplication): Builder

        fun build(): AppComponent
    }

    fun inject(app: RappiTestApplication)
}