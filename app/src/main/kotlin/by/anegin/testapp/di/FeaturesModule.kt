package by.anegin.testapp.di

import by.anegin.testapp.features.clients.data.di.ClientsDataModule
import by.anegin.testapp.features.clients.presentation.ClientsPresentationModule
import by.anegin.testapp.features.main.di.MainActivityModule
import dagger.Module

@Module(
    includes = [
        MainActivityModule::class,
        ClientsDataModule::class,
        ClientsPresentationModule::class
    ]
)
internal abstract class FeaturesModule