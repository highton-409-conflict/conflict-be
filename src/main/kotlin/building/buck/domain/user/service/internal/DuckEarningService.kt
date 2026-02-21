package building.buck.domain.user.service.internal

import building.buck.domain.collection.persistence.repository.CollectionRepositoryExtended
import building.buck.domain.collection.persistence.repository.SelectCollectionRepository
import building.buck.domain.notification.persistence.repository.NotificationRepository
import building.buck.domain.notification.persistence.repository.UserNotificationRepository
import building.buck.domain.user.persistence.DailyActivity
import building.buck.domain.user.persistence.User
import building.buck.domain.user.persistence.repository.DailyActivityRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Component
class DuckEarningService(
    private val collectionRepository: CollectionRepositoryExtended,
    private val dailyActivityRepository: DailyActivityRepository,
    private val selectCollectionRepository: SelectCollectionRepository
) {
    @Transactional
    fun earnDuckForPost(user: User) {
        val activity = dailyActivityRepository.findById(user.id.toString())
            .orElse(DailyActivity(user.id.toString()))

        if (activity.hasPostedToday) return // Already earned today

        earnDuck(user)
        activity.hasPostedToday = true
        dailyActivityRepository.save(activity)
    }

    @Transactional
    fun earnDuckForFollow(user: User) {
        val activity = dailyActivityRepository.findById(user.id.toString())
            .orElse(DailyActivity(user.id.toString()))

        if (activity.hasFollowedToday) return

        earnDuck(user)
        activity.hasFollowedToday = true
        dailyActivityRepository.save(activity)
    }

    private fun earnDuck(user: User) {
        var collection = selectCollectionRepository.findByUserId(user.id!!)?.collection ?: return

        if (collection.duck < 7) {
            collection.duck += 1
            collectionRepository.save(collection)
        }
    }
}
