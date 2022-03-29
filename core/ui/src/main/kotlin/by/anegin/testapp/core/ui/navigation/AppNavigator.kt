package by.anegin.testapp.core.ui.navigation

interface AppNavigator {

    fun navigateTo(destination: NavDestination)

    fun navigateBack(): Boolean
}