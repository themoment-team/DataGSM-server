package team.themoment.datagsm.shared.domain.student.constant

import kotlin.js.JsExport

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JsExport
@Serializable
enum class Sex(
    val value: String,
) {
    @SerialName("MAN")
    MAN("남자"),

    @SerialName("WOMAN")
    WOMAN("여자"),
    ;

    companion object {
        fun fromSex(sex: String): Sex? = entries.find { it.value == sex }
    }
}
