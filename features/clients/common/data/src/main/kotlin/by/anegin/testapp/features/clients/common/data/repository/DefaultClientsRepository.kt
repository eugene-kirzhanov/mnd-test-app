package by.anegin.testapp.features.clients.common.data.repository

import by.anegin.testapp.features.clients.common.data.repository.sources.ClientsDataSource
import by.anegin.testapp.features.clients.common.domain.models.Client
import by.anegin.testapp.features.clients.common.domain.repository.ClientsRepository
import kotlinx.coroutines.flow.Flow

internal class DefaultClientsRepository(
    private val localClientsDataSource: ClientsDataSource
) : ClientsRepository {

    override fun getClients(): Flow<List<Client>> =
        localClientsDataSource.getClients()

    override suspend fun addClient(client: Client): Long =
        localClientsDataSource.addClient(client)

    override suspend fun editClient(client: Client) {
        localClientsDataSource.editClient(client)
    }

    override suspend fun deleteClient(clientId: Long) {
        localClientsDataSource.deleteClient(clientId)
    }
}