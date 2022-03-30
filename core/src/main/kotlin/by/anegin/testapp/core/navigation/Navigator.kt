package by.anegin.testapp.core.navigation

interface Navigator {

    fun navigateTo(destination: NavDestination)

    fun navigateBack(): Boolean
}