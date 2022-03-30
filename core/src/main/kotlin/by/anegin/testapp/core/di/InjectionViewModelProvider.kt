package by.anegin.testapp.core.di

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import kotlin.reflect.KClass

class InjectionViewModelProvider<VM : ViewModel> @Inject constructor(
    private val lazyViewModel: dagger.Lazy<VM>
) {

    private val viewModelFactory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>) =
            lazyViewModel.get() as T
    }

    fun <ACTIVITY : AppCompatActivity> get(activity: ACTIVITY, viewModelClass: KClass<VM>) =
        ViewModelProvider(activity, viewModelFactory)[viewModelClass.java]

    fun <ACTIVITY : AppCompatActivity> get(activity: ACTIVITY, viewModelClass: Class<VM>) =
        ViewModelProvider(activity, viewModelFactory)[viewModelClass]

    fun <FRAGMENT : Fragment> get(fragment: FRAGMENT, viewModelClass: KClass<VM>) =
        ViewModelProvider(fragment, viewModelFactory)[viewModelClass.java]

    fun <FRAGMENT : Fragment> get(fragment: FRAGMENT, viewModelClass: Class<VM>) =
        ViewModelProvider(fragment, viewModelFactory)[viewModelClass]
}