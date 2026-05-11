package team.themoment.datagsm.common.domain.webhook.dto.payload

import com.fasterxml.jackson.annotation.JsonProperty

data class WebhookPayload(
    @field:JsonProperty("id")
    val id: String,
    @field:JsonProperty("event")
    val event: String,
    @field:JsonProperty("timestamp")
    val timestamp: String,
    @field:JsonProperty("data")
    val data: Any,
)
