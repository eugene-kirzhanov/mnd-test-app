package by.anegin.testapp.features.clients.common.domain.repository

import by.anegin.testapp.features.clients.common.domain.models.Client
import kotlinx.coroutines.flow.Flow

interface ClientsRepository {

    fun getClients(): Flow<List<Client>>

    suspend fun addClient(client: Client): Long

    suspend fun editClient(client: Client)

    suspend fun deleteClient(clientId: Long)
}