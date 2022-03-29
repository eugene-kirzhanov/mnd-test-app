package by.anegin.testapp.features.clients.common.data.repository.sources.local.mappers

import by.anegin.testapp.core.common.util.Mapper
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

internal class DateMapper @Inject constructor() : Mapper<Date, String> {

    override fun mapFromSource(source: Date): String =
        getDateFormatter().format(source)

    @Throws(IllegalArgumentException::class)
    override fun mapFromDest(dest: String): Date {
        try {
            return getDateFormatter().parse(dest)
                ?: throw ParseException("null result", 0)
        } catch (e: ParseException) {
            throw IllegalArgumentException("Invalid date format: $dest (${e.message})")
        }
    }

    private fun getDateFormatter() = SimpleDateFormat(DATE_FORMAT, Locale.ROOT)

    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd"
    }
}