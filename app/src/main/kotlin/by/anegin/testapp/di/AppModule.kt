package by.anegin.testapp.di

import by.anegin.testapp.core.navigation.AppNavigator
import by.anegin.testapp.core.util.CoroutineDispatchers
import by.anegin.testapp.features.clients.data.di.ClientsDataModule
import by.anegin.testapp.util.AppCoroutineDispatchers
import by.anegin.testapp.util.AppNavigatorDelegate
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        ClientsDataModule::class
    ]
)
@InstallIn(SingletonComponent::class)
internal interface AppModule {

    @Binds
    fun bindCoroutineDispatchers(impl: AppCoroutineDispatchers): CoroutineDispatchers

    @Binds
    fun bindAppNavigator(impl: AppNavigatorDelegate): AppNavigator
}