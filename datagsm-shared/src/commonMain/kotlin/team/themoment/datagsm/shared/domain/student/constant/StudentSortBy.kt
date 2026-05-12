package team.themoment.datagsm.shared.domain.student.constant

import kotlin.js.JsExport

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JsExport
@Serializable
enum class StudentSortBy {
    @SerialName("ID") ID,
    @SerialName("NAME") NAME,
    @SerialName("EMAIL") EMAIL,
    @SerialName("STUDENT_NUMBER") STUDENT_NUMBER,
    @SerialName("GRADE") GRADE,
    @SerialName("CLASS_NUM") CLASS_NUM,
    @SerialName("NUMBER") NUMBER,
    @SerialName("MAJOR") MAJOR,
    @SerialName("ROLE") ROLE,
    @SerialName("SEX") SEX,
    @SerialName("DORMITORY_ROOM") DORMITORY_ROOM,
}
