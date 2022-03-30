package by.anegin.testapp.di

import by.anegin.testapp.core.util.CoroutineDispatchers
import by.anegin.testapp.util.AppCoroutineDispatchers
import dagger.Binds
import dagger.Module

@Module
internal interface AppModule {

    @Binds
    fun bindCoroutineDispatchersModule(impl: AppCoroutineDispatchers): CoroutineDispatchers
}