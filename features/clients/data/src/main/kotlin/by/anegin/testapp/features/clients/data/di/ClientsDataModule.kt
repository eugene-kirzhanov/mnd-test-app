package by.anegin.testapp.features.clients.data.di

import dagger.Module

/**
 * Public @Module which exposes [RepositoriesModule] outside
 * without making its dependencies non-internal
 */
@Module(includes = [RepositoriesModule::class])
interface ClientsDataModule