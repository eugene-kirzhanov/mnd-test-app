package by.anegin.testapp.features.clients.data.repository

import by.anegin.testapp.features.clients.domain.models.Client
import kotlinx.coroutines.flow.Flow

internal interface ClientsDataSource {

    fun getClients(): Flow<List<Client>>

    suspend fun addClient(client: Client): Long

    suspend fun editClient(client: Client)

    suspend fun deleteClient(clientId: Long)
}