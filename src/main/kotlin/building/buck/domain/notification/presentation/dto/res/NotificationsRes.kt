package building.buck.domain.notification.presentation.dto.res

import building.buck.domain.notification.persistence.NotificationType
import java.time.LocalDateTime
import java.util.UUID

data class NotificationsRes(
    val notifications: List<NotificationItem>
)

data class NotificationItem(
    val id: UUID,
    val title: String,
    val body: String,
    val type: NotificationType,
    val link: UUID?,
    val createdAt: LocalDateTime
)
