package building.buck.domain.notification.service

import building.buck.domain.notification.persistence.repository.UserNotificationRepository
import building.buck.domain.notification.presentation.dto.res.NotificationItem
import building.buck.domain.notification.presentation.dto.res.NotificationsRes
import building.buck.domain.user.facade.UserFacade
import org.springframework.stereotype.Service

@Service
class QueryNotificationsService(
    private val userNotificationRepository: UserNotificationRepository,
    private val userFacade: UserFacade
) {
    fun execute(): NotificationsRes {
        val user = userFacade.currentUser()
        val userNotifications = userNotificationRepository.findAllByUserId(user.id!!)

        val items = userNotifications.map { un ->
            NotificationItem(
                id = un.notification.id!!,
                title = un.notification.title,
                body = un.notification.body,
                type = un.notification.type,
                link = un.notification.link,
                isRead = un.isRead,
                createdAt = un.notification.createdAt!!
            )
        }

        return NotificationsRes(items)
    }
}
