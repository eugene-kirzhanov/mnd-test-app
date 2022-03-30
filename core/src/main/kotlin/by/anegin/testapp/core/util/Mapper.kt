package by.anegin.testapp.core.util

interface Mapper<S : Any, D : Any> {

    fun mapFromSource(source: S): D

    fun mapFromDest(dest: D): S
}