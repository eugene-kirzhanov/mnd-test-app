package by.anegin.testapp.util

import by.anegin.testapp.core.util.CoroutineDispatchers
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Singleton
internal class AppCoroutineDispatchers @Inject constructor() : CoroutineDispatchers {

    override val io: CoroutineDispatcher = Dispatchers.IO

    override val default: CoroutineDispatcher = Dispatchers.Default

    override val main: CoroutineDispatcher = Dispatchers.Main
}