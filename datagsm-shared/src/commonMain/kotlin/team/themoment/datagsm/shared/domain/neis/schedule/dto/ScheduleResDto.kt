package team.themoment.datagsm.shared.domain.neis.schedule.dto

import kotlin.js.JsExport

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JsExport
@Suppress("NON_EXPORTABLE_TYPE")
@Serializable
data class ScheduleResDto(
    @SerialName("schedules") val schedules: List<ScheduleInfoResDto>,
)
