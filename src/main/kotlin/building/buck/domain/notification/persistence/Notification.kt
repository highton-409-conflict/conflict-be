package building.buck.domain.notification.persistence

import building.buck.global.base.BaseEntity
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "tbl_notification")
class Notification(
    @Column(nullable = false)
    val title: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    val body: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: NotificationType,

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    val link: UUID
) : BaseEntity()
