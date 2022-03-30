package by.anegin.testapp.di

import by.anegin.testapp.core.navigation.Navigator
import by.anegin.testapp.core.util.CoroutineDispatchers
import by.anegin.testapp.features.clients.data.di.ClientsDataModule
import by.anegin.testapp.util.AppCoroutineDispatchers
import by.anegin.testapp.util.AppNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Suppress("unused")
@Module(includes = [ClientsDataModule::class])
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    fun bindCoroutineDispatchers(impl: AppCoroutineDispatchers): CoroutineDispatchers

    @Binds
    fun bindAppNavigator(impl: AppNavigator): Navigator
}