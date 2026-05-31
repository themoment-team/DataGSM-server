package team.themoment.datagsm.openapi.domain.webhook.service.impl

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.datagsm.common.domain.webhook.repository.WebhookJpaRepository
import team.themoment.datagsm.openapi.domain.webhook.service.DeleteWebhookService
import team.themoment.datagsm.openapi.global.security.provider.CurrentUserProvider
import team.themoment.sdk.exception.ExpectedException

@Deprecated(
    message = "이 클래스는 datagsm-web 모듈로 이전 예정입니다. issue #344 참고",
    level = DeprecationLevel.WARNING,
)
@Suppress("DEPRECATION")
@Service
class DeleteWebhookServiceImpl(
    private val webhookJpaRepository: WebhookJpaRepository,
    private val currentUserProvider: CurrentUserProvider,
) : DeleteWebhookService {
    @Transactional
    override fun execute(webhookId: Long) {
        val account = currentUserProvider.getPrincipal().apiKey.account
        val webhook =
            webhookJpaRepository.findByIdAndAccount(webhookId, account)
                ?: throw ExpectedException("Webhook을 찾을 수 없습니다.", HttpStatus.NOT_FOUND)

        webhookJpaRepository.delete(webhook)
    }
}
