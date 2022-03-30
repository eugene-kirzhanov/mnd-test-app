package by.anegin.testapp.features.clients.presentation.list

import androidx.lifecycle.ViewModel
import by.anegin.testapp.core.navigation.AppNavigator
import by.anegin.testapp.features.clients.domain.repository.ClientsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.map

@HiltViewModel
internal class ClientsListViewModel @Inject constructor(
    clientsRepository: ClientsRepository,
    private val appNavigator: AppNavigator
) : ViewModel() {

    val clients = clientsRepository.getClients()

    val isEmptyLabelVisible = clients.map { it.isEmpty() }

    fun showAddClientScreen() {
//        appNavigator.navigateTo(ClientsNavDestinations.addClient())
    }

    fun showEditClientScreen(clientId: Long) {
//        appNavigator.navigateTo(ClientsNavDestinations.editClient(clientId))
    }
}