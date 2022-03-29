package by.anegin.testapp.features.main.ui

import androidx.lifecycle.ViewModel
import by.anegin.testapp.features.main.di.MainScreenScope
import by.anegin.testapp.features.main.router.MainScreenRouter
import javax.inject.Inject

@MainScreenScope
class MainScreenViewModel @Inject constructor(
    mainScreenRouter: MainScreenRouter
) : ViewModel() {

    init {
        mainScreenRouter.showClientsListScreen()
    }
}