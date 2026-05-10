package team.themoment.datagsm.common.domain.webhook.service

import org.springframework.http.MediaType
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Recover
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import team.themoment.sdk.logging.logger.logger
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Component
class WebhookSender {
    private val restClient: RestClient =
        RestClient
            .builder()
            .build()

    @Retryable(maxAttempts = 3, backoff = Backoff(delay = 1000, multiplier = 10.0))
    fun send(
        targetUrl: String,
        secret: String,
        payloadJson: String,
    ) {
        val signature = computeHmacSha256(secret, payloadJson)
        restClient
            .post()
            .uri(targetUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-DataGSM-Signature", "sha256=$signature")
            .body(payloadJson)
            .retrieve()
            .toBodilessEntity()
    }

    @Recover
    fun recover(
        e: Exception,
        targetUrl: String,
        secret: String,
        payloadJson: String,
    ) {
        logger().warn("Failed to deliver webhook after 3 attempts url {} error {}", targetUrl, e.message)
    }

    private fun computeHmacSha256(
        secret: String,
        payload: String,
    ): String {
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(SecretKeySpec(secret.toByteArray(Charsets.UTF_8), "HmacSHA256"))
        return mac
            .doFinal(payload.toByteArray(Charsets.UTF_8))
            .joinToString("") { "%02x".format(it) }
    }
}
