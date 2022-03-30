package by.anegin.testapp.features.clients.data.di

import dagger.Module

@Module(
    includes = [
        RepositoriesModule::class
    ]
)
interface ClientsDataModule