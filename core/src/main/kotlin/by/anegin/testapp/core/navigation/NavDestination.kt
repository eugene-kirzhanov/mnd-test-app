package by.anegin.testapp.core.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment

class NavDestination(
    val fragmentClass: Class<out Fragment>,
    val arguments: Bundle = Bundle.EMPTY,
    val addToBackStack: Boolean = true
)