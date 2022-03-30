package by.anegin.testapp.features.clients.presentation

import by.anegin.testapp.features.clients.presentation.list.di.ClientsListFragmentModule
import dagger.Module

@Module(
    includes = [
        ClientsListFragmentModule::class
    ]
)
interface ClientsPresentationModule