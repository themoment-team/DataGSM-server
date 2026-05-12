package team.themoment.datagsm.shared.domain.neis.schedule.dto

import kotlin.js.JsExport

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JsExport
@Suppress("NON_EXPORTABLE_TYPE")
@Serializable
data class ScheduleInfoResDto(
    @SerialName("schedule_id") val scheduleId: String,
    @SerialName("school_code") val schoolCode: String,
    @SerialName("school_name") val schoolName: String,
    @SerialName("office_code") val officeCode: String,
    @SerialName("office_name") val officeName: String,
    @SerialName("schedule_date") val scheduleDate: LocalDate,
    @SerialName("academic_year") val academicYear: String,
    @SerialName("event_name") val eventName: String,
    @SerialName("event_content") val eventContent: String? = null,
    @SerialName("day_category") val dayCategory: String? = null,
    @SerialName("school_course_type") val schoolCourseType: String? = null,
    @SerialName("day_night_type") val dayNightType: String? = null,
    @SerialName("target_grades") val targetGrades: List<Int>,
)
