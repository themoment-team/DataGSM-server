package team.themoment.datagsm.shared.domain.club.constant

import kotlin.js.JsExport

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JsExport
@Serializable
enum class ClubType(
    val value: String,
) {
    @SerialName("MAJOR_CLUB")
    MAJOR_CLUB("전공동아리"),

    @SerialName("AUTONOMOUS_CLUB")
    AUTONOMOUS_CLUB("창체동아리"),
}
