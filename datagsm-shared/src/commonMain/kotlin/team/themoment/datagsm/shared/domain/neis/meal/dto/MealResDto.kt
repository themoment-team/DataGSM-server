package team.themoment.datagsm.shared.domain.neis.meal.dto

import kotlin.js.JsExport

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JsExport
@Suppress("NON_EXPORTABLE_TYPE")
@Serializable
data class MealResDto(
    @SerialName("meals") val meals: List<MealInfoResDto>,
)
