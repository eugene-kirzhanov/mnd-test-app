package by.anegin.testapp.features.main.di

import by.anegin.testapp.features.main.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainActivityModule {

    @MainScreenScope
    @ContributesAndroidInjector(modules = [MainScreenModule::class])
    fun contributeMainActivity(): MainActivity
}