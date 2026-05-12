package team.themoment.datagsm.shared.domain.student.dto

import kotlin.js.JsExport

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import team.themoment.datagsm.shared.domain.student.constant.Major
import team.themoment.datagsm.shared.domain.student.constant.Sex

@JsExport
@Suppress("NON_EXPORTABLE_TYPE")
@Serializable
data class ParticipantInfoDto(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("email") val email: String,
    @SerialName("student_number") val studentNumber: Int?,
    @SerialName("major") val major: Major?,
    @SerialName("sex") val sex: Sex,
)
