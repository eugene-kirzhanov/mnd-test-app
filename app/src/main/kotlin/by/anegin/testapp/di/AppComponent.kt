package by.anegin.testapp.di

import android.content.Context
import by.anegin.testapp.ClientsApp
import by.anegin.testapp.core.common.di.ApplicationScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

@ApplicationScope
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        FeaturesModule::class
    ]
)
interface AppComponent {

    fun inject(app: ClientsApp)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}