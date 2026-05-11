package team.themoment.datagsm.common.domain.webhook.service.impl

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import team.themoment.datagsm.common.domain.webhook.dto.payload.WebhookPayload
import team.themoment.datagsm.common.domain.webhook.entity.constant.WebhookEvent
import team.themoment.datagsm.common.domain.webhook.repository.WebhookJpaRepository
import team.themoment.datagsm.common.domain.webhook.service.WebhookPublisher
import team.themoment.datagsm.common.domain.webhook.service.WebhookSender
import tools.jackson.databind.json.JsonMapper
import java.time.Instant
import java.util.UUID

@Service
class WebhookPublisherImpl(
    private val webhookJpaRepository: WebhookJpaRepository,
    private val webhookSender: WebhookSender,
) : WebhookPublisher {
    private val objectMapper = JsonMapper.builder().build()

    @Async
    override fun dispatch(
        event: WebhookEvent,
        data: Any,
    ) {
        val targets = webhookJpaRepository.findAllByEventsContainsAndIsActiveTrue(event)
        if (targets.isEmpty()) return

        val payload =
            WebhookPayload(
                id = "evt_${UUID.randomUUID().toString().replace("-", "")}",
                event = event.eventName,
                timestamp = Instant.now().toString(),
                data = data,
            )
        val payloadJson = objectMapper.writeValueAsString(payload)
        targets.forEach { webhook ->
            webhookSender.send(webhook.targetUrl, webhook.secret, payloadJson)
        }
    }
}
