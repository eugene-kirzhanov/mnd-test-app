package by.anegin.testapp.features.clients.presentation.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.anegin.testapp.core.navigation.AppNavigator
import by.anegin.testapp.features.clients.domain.models.Client
import by.anegin.testapp.features.clients.domain.models.WeightUnits
import by.anegin.testapp.features.clients.domain.repository.ClientsRepository
import by.anegin.testapp.features.clients.presentation.R
import by.anegin.testapp.features.clients.presentation.edit.birthdate.EditBirthdateFragment
import by.anegin.testapp.features.clients.presentation.edit.photo.EditPhotoFragment
import by.anegin.testapp.features.clients.presentation.edit.weight.EditWeightFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
internal class EditClientViewModel @Inject constructor(
    state: SavedStateHandle,
    private val clientsRepository: ClientsRepository,
    private val appNavigator: AppNavigator
) : ViewModel() {

    private val _client = MutableStateFlow<Client?>(null)
    val client = _client.filterNotNull()

    private val _currentPage = MutableStateFlow(0)
    val currentPage = _currentPage.asStateFlow()

    val toolbarTitle = _currentPage.map(::getPageTitle)

    val nextButtonTitle = _currentPage.map(::getNextButtonTitle)

    init {
        state.get<Long>(EditClientFragment.ARG_CLIENT_ID)?.let { clientId ->
            viewModelScope.launch {
                _client.value = clientsRepository.getClient(clientId) ?: NEW_CLIENT
            }
        } ?: run {
            _client.value = NEW_CLIENT
        }
    }

    fun onPageSelected(position: Int) {
        _currentPage.value = position
    }

    fun onBackPressed() {
        if (_currentPage.value == 0) {
            appNavigator.navigateBack()
        } else {
            _currentPage.value = _currentPage.value - 1
        }
    }

    fun onNextPressed() {
        if (_currentPage.value >= PAGES.size - 1) {
            // TODO save and exit
        } else {
            _currentPage.value = _currentPage.value + 1
        }
    }

    fun setWeight(weight: Float, units: WeightUnits) {
        val client = _client.value ?: return
        _client.value = client.copy(weight = weight, weightUnits = units)
    }

    fun setBirthdate(date: Date) {
        val client = _client.value ?: return
        _client.value = client.copy(dateOfBirth = date)
    }

    fun setPhoto(photoUri: String?) {
        val client = _client.value ?: return
        _client.value = client.copy(photoUri = photoUri)
    }

    fun clearPhoto() {
        val client = _client.value ?: return
        _client.value = client.copy(photoUri = null)
    }

    private fun getPageTitle(position: Int): Int =
        PAGES.getOrNull(position)?.titleResId ?: 0

    private fun getNextButtonTitle(position: Int): Int =
        if (position == PAGES.size - 1) R.string.done else R.string.next

    companion object {
        private val NEW_CLIENT = Client(0, 0f, WeightUnits.LB, Date(0), null)

        val PAGES = listOf(
            EditClientPage(EditWeightFragment::class.java, R.string.enter_body_weight),
            EditClientPage(EditBirthdateFragment::class.java, R.string.enter_date_of_birth),
            EditClientPage(EditPhotoFragment::class.java, R.string.photo)
        )
    }
}