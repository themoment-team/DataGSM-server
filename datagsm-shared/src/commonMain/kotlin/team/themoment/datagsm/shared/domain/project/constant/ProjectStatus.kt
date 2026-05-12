package team.themoment.datagsm.shared.domain.project.constant

import kotlin.js.JsExport

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JsExport
@Serializable
enum class ProjectStatus(
    val value: String,
) {
    @SerialName("ACTIVE")
    ACTIVE("운영 중"),

    @SerialName("ENDED")
    ENDED("종료"),
}
