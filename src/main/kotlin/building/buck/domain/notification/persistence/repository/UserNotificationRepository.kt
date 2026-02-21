package building.buck.domain.notification.persistence.repository

import building.buck.domain.notification.persistence.UserNotification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserNotificationRepository : JpaRepository<UserNotification, UUID> {
    fun findAllByUserId(userId: UUID): List<UserNotification>
}
