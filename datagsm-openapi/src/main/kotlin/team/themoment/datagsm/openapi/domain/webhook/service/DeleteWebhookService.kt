package team.themoment.datagsm.openapi.domain.webhook.service

@Deprecated(
    message = "이 인터페이스는 datagsm-web 모듈로 이전 예정입니다. issue #344 참고",
    level = DeprecationLevel.WARNING,
)
interface DeleteWebhookService {
    fun execute(webhookId: Long)
}
