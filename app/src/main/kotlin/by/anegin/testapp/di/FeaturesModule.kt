package by.anegin.testapp.di

import by.anegin.testapp.features.clients.common.data.di.ClientsCommonDataModule
import by.anegin.testapp.features.main.di.MainActivityModule
import dagger.Module

@Module(
    includes = [
        MainActivityModule::class,
        ClientsCommonDataModule::class
    ]
)
internal abstract class FeaturesModule