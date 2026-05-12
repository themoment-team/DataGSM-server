package team.themoment.datagsm.shared.domain.club.dto

import kotlin.js.JsExport

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import team.themoment.datagsm.shared.domain.club.constant.ClubType

@JsExport
@Suppress("NON_EXPORTABLE_TYPE")
@Serializable
data class ClubSummaryDto(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("type") val type: ClubType,
)
