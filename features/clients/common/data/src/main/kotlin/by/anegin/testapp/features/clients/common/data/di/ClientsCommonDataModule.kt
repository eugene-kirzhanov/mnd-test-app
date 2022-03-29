package by.anegin.testapp.features.clients.common.data.di

import dagger.Module

@Module(
    includes = [
        RepositoriesModule::class
    ]
)
interface ClientsCommonDataModule