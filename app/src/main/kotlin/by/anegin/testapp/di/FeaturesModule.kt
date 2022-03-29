package by.anegin.testapp.di

import by.anegin.testapp.features.main.di.MainActivityModule
import dagger.Module

@Module(
    includes = [
        MainActivityModule::class
    ]
)
internal abstract class FeaturesModule