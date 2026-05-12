package team.themoment.datagsm.shared.domain.neis.meal.dto

import kotlin.js.JsExport

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import team.themoment.datagsm.shared.domain.neis.meal.constant.MealType

@JsExport
@Suppress("NON_EXPORTABLE_TYPE")
@Serializable
data class MealInfoResDto(
    @SerialName("meal_id") val mealId: String,
    @SerialName("school_code") val schoolCode: String,
    @SerialName("school_name") val schoolName: String,
    @SerialName("office_code") val officeCode: String,
    @SerialName("office_name") val officeName: String,
    @SerialName("meal_date") val mealDate: LocalDate,
    @SerialName("meal_type") val mealType: MealType,
    @SerialName("meal_menu") val mealMenu: List<String>,
    @SerialName("meal_allergy_info") val mealAllergyInfo: List<String>? = null,
    @SerialName("meal_calories") val mealCalories: String? = null,
    @SerialName("origin_info") val originInfo: String? = null,
    @SerialName("nutrition_info") val nutritionInfo: String? = null,
    @SerialName("meal_serve_count") val mealServeCount: Int? = null,
)
