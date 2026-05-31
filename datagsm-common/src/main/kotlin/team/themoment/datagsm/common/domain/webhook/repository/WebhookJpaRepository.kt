package team.themoment.datagsm.common.domain.webhook.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import team.themoment.datagsm.common.domain.account.entity.AccountJpaEntity
import team.themoment.datagsm.common.domain.webhook.entity.WebhookJpaEntity
import team.themoment.datagsm.common.domain.webhook.entity.constant.WebhookEvent

@Repository
interface WebhookJpaRepository : JpaRepository<WebhookJpaEntity, Long> {
    @Deprecated(
        message = "datagsm-openapi Webhook 관리 API 이전에 따라 제거 예정입니다. issue #344 참고",
        level = DeprecationLevel.WARNING,
    )
    fun findAllByAccount(account: AccountJpaEntity): List<WebhookJpaEntity>

    @Deprecated(
        message = "datagsm-openapi Webhook 관리 API 이전에 따라 제거 예정입니다. issue #344 참고",
        level = DeprecationLevel.WARNING,
    )
    fun findByIdAndAccount(
        id: Long,
        account: AccountJpaEntity,
    ): WebhookJpaEntity?

    @Deprecated(
        message = "datagsm-openapi Webhook 관리 API 이전에 따라 제거 예정입니다. issue #344 참고",
        level = DeprecationLevel.WARNING,
    )
    fun countByAccount(account: AccountJpaEntity): Long

    fun findAllByEventsContainsAndIsActiveTrue(event: WebhookEvent): List<WebhookJpaEntity>
}
