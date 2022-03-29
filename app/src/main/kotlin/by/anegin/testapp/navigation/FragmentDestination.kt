package by.anegin.testapp.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import by.anegin.testapp.core.ui.navigation.NavDestination

class FragmentDestination(
    val fragmentClass: Class<out Fragment>,
    val arguments: Bundle,
    val addToBackStack: Boolean
) : NavDestination