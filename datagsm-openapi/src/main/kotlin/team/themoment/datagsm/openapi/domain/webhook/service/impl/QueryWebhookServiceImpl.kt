package team.themoment.datagsm.openapi.domain.webhook.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.datagsm.common.domain.webhook.dto.response.WebhookListResDto
import team.themoment.datagsm.common.domain.webhook.dto.response.WebhookResDto
import team.themoment.datagsm.common.domain.webhook.repository.WebhookJpaRepository
import team.themoment.datagsm.openapi.domain.webhook.service.QueryWebhookService
import team.themoment.datagsm.openapi.global.security.provider.CurrentUserProvider

@Deprecated(
    message = "이 클래스는 datagsm-web 모듈로 이전 예정입니다. issue #344 참고",
    level = DeprecationLevel.WARNING,
)
@Suppress("DEPRECATION")
@Service
class QueryWebhookServiceImpl(
    private val webhookJpaRepository: WebhookJpaRepository,
    private val currentUserProvider: CurrentUserProvider,
) : QueryWebhookService {
    @Transactional(readOnly = true)
    override fun execute(): WebhookListResDto {
        val account = currentUserProvider.getPrincipal().apiKey.account
        val webhooks =
            webhookJpaRepository
                .findAllByAccount(account)
                .map { webhook ->
                    WebhookResDto(
                        id = webhook.id!!,
                        targetUrl = webhook.targetUrl,
                        events = webhook.events,
                        isActive = webhook.isActive,
                        createdAt = webhook.createdAt!!,
                    )
                }
        return WebhookListResDto(webhooks = webhooks)
    }
}
