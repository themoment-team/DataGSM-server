package team.themoment.datagsm.shared.domain.club.constant

import kotlin.js.JsExport

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JsExport
@Serializable
enum class ClubSortBy {
    @SerialName("ID") ID,
    @SerialName("NAME") NAME,
    @SerialName("TYPE") TYPE,
    @SerialName("FOUNDED_YEAR") FOUNDED_YEAR,
    @SerialName("STATUS") STATUS,
}
