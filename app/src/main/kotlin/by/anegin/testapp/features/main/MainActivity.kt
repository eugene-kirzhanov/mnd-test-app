package by.anegin.testapp.features.main

import android.os.Bundle
import by.anegin.testapp.R
import by.anegin.testapp.core.di.ViewModelInjection
import by.anegin.testapp.core.navigation.AppNavigator
import by.anegin.testapp.core.navigation.NavDestination
import by.anegin.testapp.util.AppNavigatorDelegate
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(R.layout.activity_main), AppNavigator {

    private val navigatorDelegate = AppNavigatorDelegate(this, R.id.fragment_container)

    @Inject
    @ViewModelInjection
    lateinit var viewModel: MainScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.showClientsList()
        }
    }

    override fun onSupportNavigateUp(): Boolean =
        navigatorDelegate.navigateBack()

    override fun navigateTo(destination: NavDestination) =
        navigatorDelegate.navigateTo(destination)

    override fun navigateBack(): Boolean =
        navigatorDelegate.navigateBack()
}