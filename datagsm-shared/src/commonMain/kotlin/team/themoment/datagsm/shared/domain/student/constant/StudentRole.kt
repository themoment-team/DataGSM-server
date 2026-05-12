package team.themoment.datagsm.shared.domain.student.constant

import kotlin.js.JsExport

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JsExport
@Serializable
enum class StudentRole(
    val value: String,
) {
    @SerialName("STUDENT_COUNCIL")
    STUDENT_COUNCIL("학생회"),

    @SerialName("DORMITORY_MANAGER")
    DORMITORY_MANAGER("기숙사자치위원회"),

    @SerialName("GENERAL_STUDENT")
    GENERAL_STUDENT("일반학생"),

    @SerialName("GRADUATE")
    GRADUATE("졸업생"),

    @SerialName("WITHDRAWN")
    WITHDRAWN("자퇴생"),
    ;

    companion object {
        fun fromRole(role: String): StudentRole? = entries.find { it.value == role }
    }
}
