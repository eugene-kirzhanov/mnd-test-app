package by.anegin.testapp.features.main.di

import by.anegin.testapp.features.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainActivityModule {

    @ContributesAndroidInjector(modules = [MainScreenModule::class])
    fun contributeMainActivity(): MainActivity
}