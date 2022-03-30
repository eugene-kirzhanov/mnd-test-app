package by.anegin.testapp.features.clients.presentation

import android.os.Bundle
import by.anegin.testapp.core.navigation.NavDestination
import by.anegin.testapp.features.clients.presentation.edit.EditClientFragment
import by.anegin.testapp.features.clients.presentation.list.ClientsListFragment

object ClientsNavDestinations {

    fun clientsList() =
        NavDestination(
            fragmentClass = ClientsListFragment::class.java,
            arguments = Bundle.EMPTY,
            addToBackStack = false
        )

    fun addClient() =
        NavDestination(
            fragmentClass = EditClientFragment::class.java,
            arguments = Bundle.EMPTY,
            addToBackStack = true
        )

    fun editClient(clientId: Long) =
        NavDestination(
            fragmentClass = EditClientFragment::class.java,
            arguments = EditClientFragment.makeArguments(clientId),
            addToBackStack = true
        )
}