package building.buck.domain.notification.persistence.repository

import building.buck.domain.notification.persistence.Notification
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface NotificationRepository : JpaRepository<Notification, UUID>
