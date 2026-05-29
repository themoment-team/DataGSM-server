package team.themoment.datagsm.common.domain.webhook.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

data class WebhookListResDto(
    @field:Schema(description = "Webhook 목록")
    @field:JsonProperty("webhooks")
    val webhooks: List<WebhookResDto>,
)
