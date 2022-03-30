package by.anegin.testapp.features.clients.presentation.list

import androidx.lifecycle.ViewModel
import by.anegin.testapp.features.clients.domain.repository.ClientsRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.map

internal class ClientsListViewModel @Inject constructor(
    clientsRepository: ClientsRepository
) : ViewModel() {

    val clients = clientsRepository.getClients()

    val isEmptyLabelVisible = clients.map { it.isEmpty() }

    fun onClientEditClick(clientId: Long) {
        // TODO navigate to edit client
    }
}