package by.anegin.testapp.util

import by.anegin.testapp.core.common.util.CoroutineDispatchers
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal class AppCoroutineDispatchers @Inject constructor() : CoroutineDispatchers {

    override val io: CoroutineDispatcher = Dispatchers.IO

    override val default: CoroutineDispatcher = Dispatchers.Default

    override val main: CoroutineDispatcher = Dispatchers.Main
}