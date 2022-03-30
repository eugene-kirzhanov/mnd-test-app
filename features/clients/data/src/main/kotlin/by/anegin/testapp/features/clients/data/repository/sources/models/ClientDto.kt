package by.anegin.testapp.features.clients.data.repository.sources.models

/**
 * DTO model for domain Client model.
 * In real life will be used for storing data in some persistanse storage (DB, server, etc).
 * All fields are nullable (we can't be sure we get all data from server) and have primitive type.
 */
internal class ClientDto(
    val id: Long?,
    val weight: Float?,
    val weightUnits: String?,
    val dateOfBirth: Long?,
    val photoUri: String?
)