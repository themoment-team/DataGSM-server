package team.themoment.datagsm.common.domain.webhook.repository.custom.impl

import com.querydsl.jpa.impl.JPAQueryFactory
import io.github.snowykte0426.querydsl.mysql.json.jpa.JPAJsonFunctions
import org.springframework.stereotype.Repository
import team.themoment.datagsm.common.domain.webhook.entity.QWebhookJpaEntity.Companion.webhookJpaEntity
import team.themoment.datagsm.common.domain.webhook.entity.WebhookJpaEntity
import team.themoment.datagsm.common.domain.webhook.entity.constant.WebhookEvent
import team.themoment.datagsm.common.domain.webhook.repository.custom.WebhookJpaCustomRepository

@Repository
class WebhookJpaCustomRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : WebhookJpaCustomRepository {
    override fun findAllByEventAndIsActive(event: WebhookEvent): List<WebhookJpaEntity> =
        jpaQueryFactory
            .selectFrom(webhookJpaEntity)
            .where(
                webhookJpaEntity.isActive.isTrue,
                JPAJsonFunctions.jsonContainsString(webhookJpaEntity.events, event.name),
            ).fetch()
}
