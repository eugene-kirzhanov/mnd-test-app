package by.anegin.testapp.features.clients.common.data.di

import by.anegin.testapp.features.clients.common.data.repository.sources.ClientsDataSource
import by.anegin.testapp.features.clients.common.data.repository.sources.local.InMemoryClientsDataSource
import dagger.Binds
import dagger.Module

@Module(
    includes = [
        MappersModule::class
    ]
)
internal interface DataSourcesModule {

    @Binds
    fun bindLocalClientsDataSource(impl: InMemoryClientsDataSource): ClientsDataSource
}