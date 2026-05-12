package team.themoment.datagsm.shared.domain.neis.timetable.dto

import kotlin.js.JsExport

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JsExport
@Suppress("NON_EXPORTABLE_TYPE")
@Serializable
data class TimetableResDto(
    @SerialName("timetables") val timetables: List<TimetableInfoResDto>,
)
