package by.anegin.testapp.features.clients.data.repository

import by.anegin.testapp.features.clients.domain.models.Client
import by.anegin.testapp.features.clients.domain.repository.ClientsRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

internal class DefaultClientsRepository @Inject constructor(
    private val localClientsDataSource: ClientsDataSource
) : ClientsRepository {

    override fun getClients(): Flow<List<Client>> =
        localClientsDataSource.getClients()

    override suspend fun getClient(clientId: Long): Client? =
        localClientsDataSource.getClient(clientId)

    override suspend fun addClient(client: Client): Long =
        localClientsDataSource.addClient(client)

    override suspend fun editClient(client: Client) {
        localClientsDataSource.editClient(client)
    }

    override suspend fun deleteClient(clientId: Long) {
        localClientsDataSource.deleteClient(clientId)
    }
}