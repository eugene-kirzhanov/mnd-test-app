package by.anegin.testapp.di

import by.anegin.testapp.core.common.util.CoroutineDispatchers
import dagger.Binds
import dagger.Module

@Module
internal interface CoroutinesModule {

    @Binds
    fun bindCoroutineDispatchersModule(impl: by.anegin.testapp.util.AppCoroutineDispatchers): CoroutineDispatchers
}