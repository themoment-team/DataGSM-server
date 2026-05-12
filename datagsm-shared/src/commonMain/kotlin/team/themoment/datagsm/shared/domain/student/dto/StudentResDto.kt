package team.themoment.datagsm.shared.domain.student.dto

import kotlin.js.JsExport

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import team.themoment.datagsm.shared.domain.club.dto.ClubSummaryDto
import team.themoment.datagsm.shared.domain.student.constant.Major
import team.themoment.datagsm.shared.domain.student.constant.Sex
import team.themoment.datagsm.shared.domain.student.constant.StudentRole

@JsExport
@Suppress("NON_EXPORTABLE_TYPE")
@Serializable
data class StudentResDto(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("sex") val sex: Sex,
    @SerialName("email") val email: String,
    @SerialName("grade") val grade: Int?,
    @SerialName("class_num") val classNum: Int?,
    @SerialName("number") val number: Int?,
    @SerialName("student_number") val studentNumber: Int?,
    @SerialName("major") val major: Major?,
    @SerialName("specialty") val specialty: String?,
    @SerialName("role") val role: StudentRole,
    @SerialName("dormitory_floor") val dormitoryFloor: Int?,
    @SerialName("dormitory_room") val dormitoryRoom: Int?,
    @SerialName("major_club") val majorClub: ClubSummaryDto?,
    @SerialName("autonomous_club") val autonomousClub: ClubSummaryDto?,
    @SerialName("github_id") val githubId: String?,
    @SerialName("github_url") val githubUrl: String?,
)
