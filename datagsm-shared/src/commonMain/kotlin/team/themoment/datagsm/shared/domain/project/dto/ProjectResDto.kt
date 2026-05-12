package team.themoment.datagsm.shared.domain.project.dto

import kotlin.js.JsExport

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import team.themoment.datagsm.shared.domain.club.dto.ClubSummaryDto
import team.themoment.datagsm.shared.domain.project.constant.ProjectStatus
import team.themoment.datagsm.shared.domain.student.dto.ParticipantInfoDto

@JsExport
@Suppress("NON_EXPORTABLE_TYPE")
@Serializable
data class ProjectResDto(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("start_year") val startYear: Int,
    @SerialName("end_year") val endYear: Int?,
    @SerialName("status") val status: ProjectStatus,
    @SerialName("club") val club: ClubSummaryDto?,
    @SerialName("participants") val participants: List<ParticipantInfoDto>,
)
