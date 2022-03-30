package by.anegin.testapp.features.clients.presentation.edit

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

data class EditClientPage(
    val fragmentClass: Class<out Fragment>,
    @StringRes val titleResId: Int
)