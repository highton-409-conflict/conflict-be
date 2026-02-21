package building.buck.domain.collection.batch

import building.buck.domain.collection.persistence.repository.CollectionRepositoryExtended
import building.buck.domain.notification.persistence.Notification
import building.buck.domain.notification.persistence.NotificationType
import building.buck.domain.notification.persistence.UserNotification
import building.buck.domain.notification.persistence.repository.NotificationRepository
import building.buck.domain.notification.persistence.repository.UserNotificationRepository
import building.buck.domain.user.persistence.repository.DailyActivityRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Component
class DuckResetBatchJob(
    private val collectionRepository: CollectionRepositoryExtended,
    private val dailyActivityRepository: DailyActivityRepository,
    private val notificationRepository: NotificationRepository,
    private val userNotificationRepository: UserNotificationRepository
) {
    @Scheduled(cron = "0 0 0 * * *") // Every day at midnight
    @Transactional
    fun resetInactiveCollections() {
        val allUserCollections = collectionRepository.findAll()

        allUserCollections.forEach {
            // Skip completed collections (duck == 7)
            if (it.duck == 7) return@forEach

            // Check if user was active yesterday
            val activity = dailyActivityRepository.findById(it.user.id.toString())
                .orElse(null)

            // If no activity or both flags false, reset duck
            if (activity == null || (!activity.hasPostedToday && !activity.hasFollowedToday)) {
                if (it.duck > 0) {
                    // Reset duck using custom repository method (duck field is immutable)
                    it.duck = 0

                    // Send notification
                    val notification = notificationRepository.save(
                        Notification(
                            title = "Collection Reset",
                            body = "Your collection progress was reset due to inactivity",
                            type = NotificationType.COLLECTION,
                            link = it.id!!
                        )
                    )

                    userNotificationRepository.save(
                        UserNotification(
                            user = it.user,
                            notification = notification
                        )
                    )
                }
            }
        }

        // Clear all activity flags for next day
        dailyActivityRepository.deleteAll()
    }
}
