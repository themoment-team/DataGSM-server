package team.themoment.datagsm.shared.domain.student.constant

import kotlin.js.JsExport

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JsExport
@Serializable
enum class Major(
    val value: String,
) {
    @SerialName("SW_DEVELOPMENT")
    SW_DEVELOPMENT("SW개발과"),

    @SerialName("SMART_IOT")
    SMART_IOT("스마트IoT과"),

    @SerialName("AI")
    AI("인공지능과"),
    ;

    companion object {
        fun fromClassNum(classNum: Int): Major? =
            when (classNum) {
                1, 2 -> SW_DEVELOPMENT
                3 -> SMART_IOT
                4 -> AI
                else -> null
            }

        fun fromMajor(major: String): Major? = entries.find { it.value == major }
    }
}
