package team.themoment.datagsm.shared.domain.club.dto

import kotlin.js.JsExport

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import team.themoment.datagsm.shared.domain.club.constant.ClubStatus
import team.themoment.datagsm.shared.domain.club.constant.ClubType
import team.themoment.datagsm.shared.domain.student.dto.ParticipantInfoDto

@JsExport
@Suppress("NON_EXPORTABLE_TYPE")
@Serializable
data class ClubResDto(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("type") val type: ClubType,
    @SerialName("leader") val leader: ParticipantInfoDto? = null,
    @SerialName("participants") val participants: List<ParticipantInfoDto>,
    @SerialName("founded_year") val foundedYear: Int,
    @SerialName("status") val status: ClubStatus,
    @SerialName("abolished_year") val abolishedYear: Int? = null,
)
