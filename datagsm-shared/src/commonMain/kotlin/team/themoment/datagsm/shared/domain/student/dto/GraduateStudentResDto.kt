package team.themoment.datagsm.shared.domain.student.dto

import kotlin.js.JsExport

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JsExport
@Serializable
data class GraduateStudentResDto(
    @SerialName("graduated_count") val graduatedCount: Int,
)
