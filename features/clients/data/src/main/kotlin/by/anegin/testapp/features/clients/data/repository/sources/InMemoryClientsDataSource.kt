package by.anegin.testapp.features.clients.data.repository.sources

import by.anegin.testapp.core.util.CoroutineDispatchers
import by.anegin.testapp.core.util.Mapper
import by.anegin.testapp.features.clients.data.repository.ClientsDataSource
import by.anegin.testapp.features.clients.data.repository.sources.models.ClientDto
import by.anegin.testapp.features.clients.domain.models.Client
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

/**
 * Local [ClientsDataSource] which stores clients list in StateFlow,
 * allowing callers to observe changes.
 *
 * The datasource operates in default-dispatcher.
 *
 * Data is stored in separate [ClientDto] models, which in real app
 * will differ from domain [Client] model. See [ClientDto] for details.
 */
@Singleton
internal class InMemoryClientsDataSource @Inject constructor(
    private val dispatchers: CoroutineDispatchers,
    private val clientDtoMapper: Mapper<Client, ClientDto>
) : ClientsDataSource {

    private val clients = MutableStateFlow<List<ClientDto>>(emptyList())
    private val lock = Mutex()

    override fun getClients(): Flow<List<Client>> =
        clients
            .map { clientDtos ->
                // map DTO models to domain models
                clientDtos.mapNotNull { clientDto ->
                    try {
                        clientDtoMapper.mapFromDest(clientDto)
                    } catch (e: IllegalArgumentException) {
                        // skip DTOs which can't be mapped
                        null
                    }
                }
            }
            .flowOn(dispatchers.default)

    override suspend fun getClient(clientId: Long): Client? = withContext(dispatchers.default) {
        lock.withLock {
            val clientDto = clients.value.find { it.id == clientId }
            clientDto?.let { clientDtoMapper.mapFromDest(it) }
        }
    }

    override suspend fun addClient(client: Client): Long = withContext(dispatchers.default) {
        lock.withLock {
            val clientsList = clients.value

            // use incremental value as ID for new clients
            val newClientId = clientsList.size.toLong() + 1 // staring from 1

            clients.value = clientsList.toMutableList()
                .apply {
                    val clientDto = clientDtoMapper.mapFromSource(client.copy(id = newClientId))
                    add(clientDto)
                }

            newClientId
        }
    }

    override suspend fun editClient(client: Client) = withContext(dispatchers.default) {
        lock.withLock {
            val clientsList = clients.value
            val index = clientsList.indexOfFirst { it.id == client.id }
            if (index != -1) {
                clients.value = clientsList.toMutableList()
                    .apply {
                        val clientDto = clientDtoMapper.mapFromSource(client)
                        set(index, clientDto)
                    }
            }
        }
    }

    override suspend fun deleteClient(clientId: Long) = withContext(dispatchers.default) {
        lock.withLock {
            clients.value = clients.value.toMutableList()
                .apply {
                    removeAll { it.id == clientId }
                }
        }
    }
}