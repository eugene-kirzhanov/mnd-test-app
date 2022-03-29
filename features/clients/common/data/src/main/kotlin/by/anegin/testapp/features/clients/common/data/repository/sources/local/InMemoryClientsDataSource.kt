package by.anegin.testapp.features.clients.common.data.repository.sources.local

import by.anegin.testapp.core.common.di.ApplicationScope
import by.anegin.testapp.core.common.util.CoroutineDispatchers
import by.anegin.testapp.core.common.util.Mapper
import by.anegin.testapp.features.clients.common.data.repository.sources.ClientsDataSource
import by.anegin.testapp.features.clients.common.data.repository.sources.local.models.ClientDto
import by.anegin.testapp.features.clients.common.domain.models.Client
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import timber.log.Timber

@ApplicationScope
internal class InMemoryClientsDataSource @Inject constructor(
    private val dispatchers: CoroutineDispatchers,
    private val clientDtoMapper: Mapper<Client, ClientDto>
) : ClientsDataSource {

    private val clients = MutableStateFlow<List<ClientDto>>(emptyList())
    private val lock = Mutex()

    override fun getClients(): Flow<List<Client>> =
        clients
            .map { clientDtos ->
                clientDtos.mapNotNull { clientDto ->
                    try {
                        clientDtoMapper.mapFromDest(clientDto)
                    } catch (e: IllegalArgumentException) {
                        // skip DTOs which can't be mapped
                        Timber.w("Error mapping ClientDto: ${e.message}")
                        null
                    }
                }
            }
            .flowOn(dispatchers.default)

    override suspend fun addClient(client: Client): Long = withContext(dispatchers.default) {
        lock.withLock {
            val clientsList = clients.value
            val newClientId = clientsList.size.toLong()

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