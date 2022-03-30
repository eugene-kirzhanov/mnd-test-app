package by.anegin.testapp.features.clients.presentation.list.di

import by.anegin.testapp.features.clients.presentation.list.ClientsListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal interface ClientsListFragmentModule {

    @ContributesAndroidInjector(modules = [ClientsListScreenModule::class])
    fun contributeClientsListFragment(): ClientsListFragment
}