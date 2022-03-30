package by.anegin.testapp.core.navigation

interface AppNavigator {

    fun navigateTo(destination: NavDestination)

    fun navigateBack(): Boolean
}