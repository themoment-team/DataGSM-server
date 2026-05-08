package team.themoment.datagsm.openapi.domain.webhook.service

interface DeleteWebhookService {
    fun execute(webhookId: Long)
}
