package team.themoment.datagsm.shared.domain.club.dto

import kotlin.js.JsExport

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JsExport
@Suppress("NON_EXPORTABLE_TYPE")
@Serializable
data class ClubListResDto(
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_elements") val totalElements: Long,
    @SerialName("clubs") val clubs: List<ClubResDto>,
)
