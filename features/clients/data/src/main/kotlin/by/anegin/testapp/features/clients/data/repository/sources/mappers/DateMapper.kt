package by.anegin.testapp.features.clients.data.repository.sources.mappers

import by.anegin.testapp.core.util.Mapper
import java.util.Date
import javax.inject.Inject

internal class DateMapper @Inject constructor() : Mapper<Date, Long> {

    override fun mapFromSource(source: Date): Long = source.time

    override fun mapFromDest(dest: Long): Date = Date(dest)
}