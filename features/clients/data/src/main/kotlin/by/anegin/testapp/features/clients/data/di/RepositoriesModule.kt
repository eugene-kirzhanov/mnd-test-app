package by.anegin.testapp.features.clients.data.di

import by.anegin.testapp.core.util.Mapper
import by.anegin.testapp.features.clients.data.repository.ClientsDataSource
import by.anegin.testapp.features.clients.data.repository.DefaultClientsRepository
import by.anegin.testapp.features.clients.data.repository.sources.InMemoryClientsDataSource
import by.anegin.testapp.features.clients.data.repository.sources.mappers.ClientDtoMapper
import by.anegin.testapp.features.clients.data.repository.sources.mappers.DateMapper
import by.anegin.testapp.features.clients.data.repository.sources.mappers.WeightUnitsMapper
import by.anegin.testapp.features.clients.data.repository.sources.models.ClientDto
import by.anegin.testapp.features.clients.domain.models.Client
import by.anegin.testapp.features.clients.domain.models.WeightUnits
import by.anegin.testapp.features.clients.domain.repository.ClientsRepository
import dagger.Binds
import dagger.Module
import java.util.Date

@Module
internal interface RepositoriesModule {

    @Binds
    fun bindClientsRepository(impl: DefaultClientsRepository): ClientsRepository

    @Binds
    fun bindLocalClientsDataSource(impl: InMemoryClientsDataSource): ClientsDataSource

    @Binds
    fun bindClientDtoMapper(impl: ClientDtoMapper): Mapper<Client, ClientDto>

    @Binds
    fun bindDateMapper(impl: DateMapper): Mapper<Date, Long>

    @Binds
    fun bindWeightUnitsMapper(impl: WeightUnitsMapper): Mapper<WeightUnits, String>
}