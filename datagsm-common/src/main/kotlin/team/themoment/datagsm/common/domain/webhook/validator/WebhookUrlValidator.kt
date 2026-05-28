package team.themoment.datagsm.common.domain.webhook.validator

import java.net.InetAddress
import java.net.URI
import java.net.UnknownHostException

object WebhookUrlValidator {
    fun isPrivateOrLocalUrl(url: String): Boolean =
        try {
            val host = URI(url).host ?: return true
            val addresses =
                try {
                    InetAddress.getAllByName(host)
                } catch (e: UnknownHostException) {
                    emptyArray()
                }
            addresses.any { address ->
                address.isLoopbackAddress ||
                    address.isSiteLocalAddress ||
                    address.isLinkLocalAddress ||
                    address.isAnyLocalAddress
            }
        } catch (e: Exception) {
            true
        }
}
