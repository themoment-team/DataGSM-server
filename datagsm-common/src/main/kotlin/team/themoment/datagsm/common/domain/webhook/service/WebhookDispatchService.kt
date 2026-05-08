package team.themoment.datagsm.common.domain.webhook.service

import team.themoment.datagsm.common.domain.webhook.entity.constant.WebhookEvent

interface WebhookDispatchService {
    fun dispatch(
        event: WebhookEvent,
        data: Any,
    )
}
