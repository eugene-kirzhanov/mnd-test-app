package by.anegin.testapp.features.clients.common.data.di

import by.anegin.testapp.core.common.util.Mapper
import by.anegin.testapp.features.clients.common.data.repository.sources.local.mappers.ClientDtoMapper
import by.anegin.testapp.features.clients.common.data.repository.sources.local.mappers.DateMapper
import by.anegin.testapp.features.clients.common.data.repository.sources.local.mappers.WeightUnitsMapper
import by.anegin.testapp.features.clients.common.data.repository.sources.local.models.ClientDto
import by.anegin.testapp.features.clients.common.domain.models.Client
import by.anegin.testapp.features.clients.common.domain.models.WeightUnits
import dagger.Binds
import dagger.Module
import java.util.Date

@Module
internal interface MappersModule {

    @Binds
    fun bindClientDtoMapper(impl: ClientDtoMapper): Mapper<Client, ClientDto>

    @Binds
    fun bindDateMapper(impl: DateMapper): Mapper<Date, String>

    @Binds
    fun bindWeightUnitsMapper(impl: WeightUnitsMapper): Mapper<WeightUnits, String>
}