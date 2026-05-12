package team.themoment.datagsm.shared.domain.neis.timetable.dto

import kotlin.js.JsExport

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JsExport
@Suppress("NON_EXPORTABLE_TYPE")
@Serializable
data class TimetableInfoResDto(
    @SerialName("timetable_id") val timetableId: String,
    @SerialName("school_code") val schoolCode: String,
    @SerialName("school_name") val schoolName: String,
    @SerialName("office_code") val officeCode: String,
    @SerialName("office_name") val officeName: String,
    @SerialName("timetable_date") val timetableDate: LocalDate,
    @SerialName("academic_year") val academicYear: String,
    @SerialName("semester") val semester: String? = null,
    @SerialName("grade") val grade: Int,
    @SerialName("class_num") val classNum: Int,
    @SerialName("period") val period: Int,
    @SerialName("subject") val subject: String? = null,
)
