package team.themoment.datagsm.common.domain.webhook.repository.custom

import team.themoment.datagsm.common.domain.webhook.entity.WebhookJpaEntity
import team.themoment.datagsm.common.domain.webhook.entity.constant.WebhookEvent

interface WebhookJpaCustomRepository {
    fun findAllByEventAndIsActive(event: WebhookEvent): List<WebhookJpaEntity>
}
