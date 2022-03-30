package by.anegin.testapp.features.clients.data.repository.sources.mappers

import by.anegin.testapp.core.util.Mapper
import by.anegin.testapp.features.clients.domain.models.WeightUnits
import javax.inject.Inject

internal class WeightUnitsMapper @Inject constructor() : Mapper<WeightUnits, String> {

    override fun mapFromSource(source: WeightUnits): String = when (source) {
        WeightUnits.LB -> WEIGHT_UNIT_LB
        WeightUnits.KG -> WEIGHT_UNIT_KB
    }

    @Throws(IllegalArgumentException::class)
    override fun mapFromDest(dest: String): WeightUnits = when (dest) {
        WEIGHT_UNIT_LB -> WeightUnits.LB
        WEIGHT_UNIT_KB -> WeightUnits.KG
        else -> throw IllegalArgumentException("Unknown WeightUnit type: $dest")
    }

    companion object {
        private const val WEIGHT_UNIT_LB = "LB"
        private const val WEIGHT_UNIT_KB = "KG"
    }
}