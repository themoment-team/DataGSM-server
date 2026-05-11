package team.themoment.datagsm.openapi.domain.webhook.service.impl

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.datagsm.common.domain.webhook.dto.request.ModifyWebhookReqDto
import team.themoment.datagsm.common.domain.webhook.dto.response.WebhookResDto
import team.themoment.datagsm.common.domain.webhook.repository.WebhookJpaRepository
import team.themoment.datagsm.openapi.domain.webhook.service.ModifyWebhookService
import team.themoment.datagsm.openapi.global.security.provider.CurrentUserProvider
import team.themoment.sdk.exception.ExpectedException

@Service
class ModifyWebhookServiceImpl(
    private val webhookJpaRepository: WebhookJpaRepository,
    private val currentUserProvider: CurrentUserProvider,
) : ModifyWebhookService {
    @Transactional
    override fun execute(
        webhookId: Long,
        reqDto: ModifyWebhookReqDto,
    ): WebhookResDto {
        val account = currentUserProvider.getPrincipal().apiKey.account
        val webhook =
            webhookJpaRepository.findByIdAndAccount(webhookId, account)
                ?: throw ExpectedException("Webhook을 찾을 수 없습니다.", HttpStatus.NOT_FOUND)

        reqDto.targetUrl?.let { webhook.targetUrl = it }
        reqDto.events?.let {
            webhook.events.clear()
            webhook.events.addAll(it)
        }

        return WebhookResDto(
            id = webhook.id!!,
            targetUrl = webhook.targetUrl,
            events = webhook.events,
            isActive = webhook.isActive,
            createdAt = webhook.createdAt!!,
        )
    }
}
