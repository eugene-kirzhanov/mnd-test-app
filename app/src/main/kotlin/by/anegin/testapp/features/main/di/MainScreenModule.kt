package by.anegin.testapp.features.main.di

import by.anegin.testapp.core.di.InjectionViewModelProvider
import by.anegin.testapp.core.di.ViewModelInjection
import by.anegin.testapp.core.navigation.AppNavigator
import by.anegin.testapp.features.main.MainActivity
import by.anegin.testapp.features.main.MainScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface MainScreenModule {

    @Binds
    fun bindAppNavigator(activity: MainActivity): AppNavigator

    companion object {
        @Provides
        @ViewModelInjection
        fun provideViewModel(
            activity: MainActivity,
            viewModelProvider: InjectionViewModelProvider<MainScreenViewModel>
        ): MainScreenViewModel {
            return viewModelProvider.get(activity, MainScreenViewModel::class)
        }
    }
}