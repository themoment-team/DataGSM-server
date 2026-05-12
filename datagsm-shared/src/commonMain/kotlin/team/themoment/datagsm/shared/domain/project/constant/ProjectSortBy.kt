package team.themoment.datagsm.shared.domain.project.constant

import kotlin.js.JsExport

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JsExport
@Serializable
enum class ProjectSortBy {
    @SerialName("ID") ID,
    @SerialName("NAME") NAME,
}
