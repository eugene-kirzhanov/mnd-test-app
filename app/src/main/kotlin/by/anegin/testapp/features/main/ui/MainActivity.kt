package by.anegin.testapp.features.main.ui

import android.os.Bundle
import by.anegin.testapp.R
import by.anegin.testapp.core.ui.di.ViewModelInjection
import by.anegin.testapp.core.ui.navigation.AppNavigator
import by.anegin.testapp.core.ui.navigation.NavDestination
import by.anegin.testapp.navigation.AppNavigatorDelegate
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(R.layout.activity_main), AppNavigator {

    private val appNavigator: AppNavigator by lazy {
        AppNavigatorDelegate(this, R.id.fragment_container)
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: MainScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onSupportNavigateUp(): Boolean =
        appNavigator.navigateBack()

    override fun navigateTo(destination: NavDestination) =
        appNavigator.navigateTo(destination)

    override fun navigateBack(): Boolean =
        appNavigator.navigateBack()
}