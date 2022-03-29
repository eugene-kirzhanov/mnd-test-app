package by.anegin.testapp.features.clients.common.domain.models

import java.util.Date

data class Client(
    val id: Long,
    val weight: Float,
    val weightUnits: WeightUnits,
    val dateOfBirth: Date,
    val photoUri: String?
)