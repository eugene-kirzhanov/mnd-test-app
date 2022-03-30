package by.anegin.testapp.features.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import by.anegin.testapp.R
import by.anegin.testapp.core.navigation.AppNavigator
import by.anegin.testapp.util.AppNavigatorDelegate
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    @Inject
    lateinit var appNavigator: AppNavigator

    private val viewModel: MainScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (appNavigator as? AppNavigatorDelegate)?.setup(this, R.id.fragment_container)

        if (savedInstanceState == null) {
            viewModel.showClientsList()
        }
    }

    override fun onDestroy() {
        (appNavigator as? AppNavigatorDelegate)?.cleanup()
        super.onDestroy()
    }
}