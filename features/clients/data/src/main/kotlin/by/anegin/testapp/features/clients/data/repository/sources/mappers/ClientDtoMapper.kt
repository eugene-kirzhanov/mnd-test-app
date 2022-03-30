package by.anegin.testapp.features.clients.data.repository.sources.mappers

import by.anegin.testapp.core.util.Mapper
import by.anegin.testapp.features.clients.data.repository.sources.models.ClientDto
import by.anegin.testapp.features.clients.domain.models.Client
import by.anegin.testapp.features.clients.domain.models.WeightUnits
import java.util.Date
import javax.inject.Inject

internal class ClientDtoMapper @Inject constructor(
    private val dateMapper: Mapper<Date, Long>,
    private val weightUnitsMapper: Mapper<WeightUnits, String>
) : Mapper<Client, ClientDto> {

    override fun mapFromSource(source: Client): ClientDto =
        ClientDto(
            id = source.id,
            weight = source.weight,
            weightUnits = weightUnitsMapper.mapFromSource(source.weightUnits),
            dateOfBirth = dateMapper.mapFromSource(source.dateOfBirth),
            photoUri = source.photoUri
        )

    @Throws(IllegalArgumentException::class)
    override fun mapFromDest(dest: ClientDto): Client {
        val clientId = dest.id
            ?: throw IllegalArgumentException("Client ID is null")
        val clientWeight = dest.weight
            ?: throw IllegalArgumentException("Client weight is null")
        val clientWeightUnits = dest.weightUnits
            ?: throw IllegalArgumentException("Client weight units is null")
        val clientBirthdate = dest.dateOfBirth
            ?: throw IllegalArgumentException("Client birthdate is null")

        return Client(
            id = clientId,
            weight = clientWeight,
            weightUnits = weightUnitsMapper.mapFromDest(clientWeightUnits),
            dateOfBirth = dateMapper.mapFromDest(clientBirthdate),
            photoUri = dest.photoUri
        )
    }
}