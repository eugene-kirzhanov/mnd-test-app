package by.anegin.testapp.features.clients.presentation

import android.os.Bundle
import by.anegin.testapp.core.navigation.NavDestination
import by.anegin.testapp.features.clients.presentation.list.ClientsListFragment

object ClientsNavDestinations {

    fun clientsList() =
        NavDestination(
            fragmentClass = ClientsListFragment::class.java,
            arguments = Bundle.EMPTY,
            addToBackStack = false
        )
}