package team.themoment.datagsm.common.global.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import team.themoment.datagsm.common.domain.webhook.entity.constant.WebhookEvent
import tools.jackson.core.type.TypeReference
import tools.jackson.databind.ObjectMapper

@Converter
class WebhookEventSetConverter : AttributeConverter<Set<WebhookEvent>, String> {
    companion object {
        private val objectMapper = ObjectMapper()
    }

    override fun convertToDatabaseColumn(attribute: Set<WebhookEvent>): String = objectMapper.writeValueAsString(attribute.map { it.name })

    override fun convertToEntityAttribute(dbData: String?): Set<WebhookEvent> =
        if (dbData.isNullOrEmpty()) {
            emptySet()
        } else {
            objectMapper
                .readValue(dbData, object : TypeReference<Set<String>>() {})
                .mapNotNull { name -> WebhookEvent.entries.find { it.name == name } }
                .toSet()
        }
}
