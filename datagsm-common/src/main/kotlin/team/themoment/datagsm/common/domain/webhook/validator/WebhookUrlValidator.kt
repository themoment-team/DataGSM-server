package team.themoment.datagsm.common.domain.webhook.validator

import java.net.InetAddress
import java.net.URI

object WebhookUrlValidator {
    fun isPrivateOrLocalUrl(url: String): Boolean =
        try {
            val host = URI(url).host ?: return true
            val address = InetAddress.getByName(host)
            address.isLoopbackAddress ||
                address.isSiteLocalAddress ||
                address.isLinkLocalAddress ||
                address.isAnyLocalAddress
        } catch (e: Exception) {
            false
        }
}
