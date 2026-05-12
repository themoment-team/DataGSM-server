package team.themoment.datagsm.shared.domain.neis.meal.constant

import kotlin.js.JsExport

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JsExport
@Serializable
enum class MealType {
    @SerialName("BREAKFAST") BREAKFAST,
    @SerialName("LUNCH") LUNCH,
    @SerialName("DINNER") DINNER,
}
