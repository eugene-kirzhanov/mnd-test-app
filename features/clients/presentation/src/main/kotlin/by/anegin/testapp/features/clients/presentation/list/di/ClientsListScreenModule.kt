package by.anegin.testapp.features.clients.presentation.list.di

import by.anegin.testapp.core.di.InjectionViewModelProvider
import by.anegin.testapp.core.di.ViewModelInjection
import by.anegin.testapp.features.clients.presentation.list.ClientsListFragment
import by.anegin.testapp.features.clients.presentation.list.ClientsListViewModel
import dagger.Module
import dagger.Provides

@Module
internal interface ClientsListScreenModule {

    companion object {
        @Provides
        @ViewModelInjection
        fun provideViewModel(
            fragment: ClientsListFragment,
            viewModelProvider: InjectionViewModelProvider<ClientsListViewModel>
        ): ClientsListViewModel {
            return viewModelProvider.get(fragment, ClientsListViewModel::class)
        }
    }
}