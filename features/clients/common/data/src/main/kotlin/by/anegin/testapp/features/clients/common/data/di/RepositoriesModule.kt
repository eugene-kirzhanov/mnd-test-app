package by.anegin.testapp.features.clients.common.data.di

import by.anegin.testapp.features.clients.common.data.repository.DefaultClientsRepository
import by.anegin.testapp.features.clients.common.domain.repository.ClientsRepository
import dagger.Binds
import dagger.Module

@Module(
    includes = [
        DataSourcesModule::class
    ]
)
internal interface RepositoriesModule {

    @Binds
    fun bindClientsRepository(impl: DefaultClientsRepository): ClientsRepository
}