package team.themoment.datagsm.common.domain.webhook.entity

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.DynamicUpdate
import team.themoment.datagsm.common.domain.account.entity.AccountJpaEntity
import team.themoment.datagsm.common.domain.webhook.entity.constant.WebhookEvent
import team.themoment.datagsm.common.global.converter.WebhookEventSetConverter
import java.time.LocalDateTime

@Table(name = "tb_webhook")
@Entity
@DynamicUpdate
class WebhookJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null

    @Column(name = "target_url", nullable = false, length = 2048)
    lateinit var targetUrl: String

    @Column(name = "secret", nullable = false, length = 64)
    lateinit var secret: String

    @Convert(converter = WebhookEventSetConverter::class)
    @Column(name = "events", columnDefinition = "json", nullable = false)
    var events: Set<WebhookEvent> = emptySet()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, referencedColumnName = "id")
    lateinit var account: AccountJpaEntity

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime? = null
}
