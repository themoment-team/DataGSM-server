package team.themoment.datagsm.shared.domain.club.constant

import kotlin.js.JsExport

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JsExport
@Serializable
enum class ClubStatus(
    val value: String,
) {
    @SerialName("ACTIVE")
    ACTIVE("운영 중"),

    @SerialName("ABOLISHED")
    ABOLISHED("폐지"),
}
