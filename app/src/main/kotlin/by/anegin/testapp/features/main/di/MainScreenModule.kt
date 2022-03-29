package by.anegin.testapp.features.main.di

import by.anegin.testapp.core.ui.di.InjectionViewModelProvider
import by.anegin.testapp.core.ui.di.ViewModelInjection
import by.anegin.testapp.core.ui.navigation.AppNavigator
import by.anegin.testapp.features.main.router.MainScreenRouter
import by.anegin.testapp.features.main.router.MainScreenRouterImpl
import by.anegin.testapp.features.main.ui.MainActivity
import by.anegin.testapp.features.main.ui.MainScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface MainScreenModule {

    @Binds
    fun bindAppNavigator(activity: MainActivity): AppNavigator

    @Binds
    fun bindMainScreenRouter(impl: MainScreenRouterImpl): MainScreenRouter

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