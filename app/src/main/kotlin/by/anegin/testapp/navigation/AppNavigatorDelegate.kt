package by.anegin.testapp.navigation

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import by.anegin.testapp.core.ui.navigation.AppNavigator
import by.anegin.testapp.core.ui.navigation.NavDestination

class AppNavigatorDelegate(
    private val activity: AppCompatActivity,
    @IdRes private val fragmentContainerId: Int
) : AppNavigator {

    override fun navigateTo(destination: NavDestination) {
        if (destination !is FragmentDestination) return
        activity.supportFragmentManager.commit {
            replace(fragmentContainerId, destination.fragmentClass, destination.arguments)
            if (destination.addToBackStack) {
                addToBackStack(destination.fragmentClass.simpleName)
            }
        }
    }

    override fun navigateBack(): Boolean {
        return if (activity.supportFragmentManager.backStackEntryCount > 0) {
            activity.supportFragmentManager.popBackStack()
            true
        } else {
            false
        }
    }
}