package by.anegin.testapp.core.util

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatchers {

    val io: CoroutineDispatcher

    val default: CoroutineDispatcher

    val main: CoroutineDispatcher
}