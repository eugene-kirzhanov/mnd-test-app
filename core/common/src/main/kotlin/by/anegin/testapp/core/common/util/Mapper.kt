package by.anegin.testapp.core.common.util

interface Mapper<S : Any, D : Any> {

    fun mapFromSource(source: S): D

    fun mapFromDest(dest: D): S
}