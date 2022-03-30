package by.anegin.testapp.features.clients.domain.repository

import by.anegin.testapp.features.clients.domain.models.Client
import kotlinx.coroutines.flow.Flow

interface ClientsRepository {

    fun getClients(): Flow<List<Client>>

    suspend fun getClient(clientId: Long): Client?

    suspend fun addClient(client: Client): Long

    suspend fun editClient(client: Client)

    suspend fun deleteClient(clientId: Long)
}