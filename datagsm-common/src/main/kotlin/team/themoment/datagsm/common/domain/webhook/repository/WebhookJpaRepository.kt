package team.themoment.datagsm.common.domain.webhook.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import team.themoment.datagsm.common.domain.account.entity.AccountJpaEntity
import team.themoment.datagsm.common.domain.webhook.entity.WebhookJpaEntity

@Repository
interface WebhookJpaRepository : JpaRepository<WebhookJpaEntity, Long> {
    fun findAllByAccount(account: AccountJpaEntity): List<WebhookJpaEntity>

    fun findByIdAndAccount(
        id: Long,
        account: AccountJpaEntity,
    ): WebhookJpaEntity?

    fun findAllByIsActiveTrue(): List<WebhookJpaEntity>

    fun countByAccount(account: AccountJpaEntity): Long
}
