package building.buck.domain.user.service.internal

import building.buck.domain.collection.persistence.repository.CollectionRepository
import building.buck.domain.collection.persistence.repository.SelectCollectionRepository
import building.buck.domain.user.persistence.DailyActivity
import building.buck.domain.user.persistence.User
import building.buck.domain.user.persistence.repository.DailyActivityRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DuckEarningService(
    private val collectionRepository: CollectionRepository,
    private val dailyActivityRepository: DailyActivityRepository,
    private val selectCollectionRepository: SelectCollectionRepository
) {
    private val logger = LoggerFactory.getLogger(DuckEarningService::class.java)

    @Transactional
    fun earnDuckForPost(user: User) {
        logger.info("earnDuckForPost called for user: ${user.id}")

        val activity = dailyActivityRepository.findById(user.id.toString())
            .orElse(DailyActivity(user.id.toString()))

        if (activity.hasPostedToday) {
            logger.info("User ${user.id} has already posted today")
            return
        }

        // earnDuck이 성공한 경우에만 activity 업데이트
        val success = earnDuck(user)
        if (success) {
            activity.hasPostedToday = true
            dailyActivityRepository.save(activity)
            logger.info("Daily activity saved for user: ${user.id}")
        }
    }

    @Transactional
    fun earnDuckForFollow(user: User) {
        val activity = dailyActivityRepository.findById(user.id.toString())
            .orElse(DailyActivity(user.id.toString()))

        if (activity.hasFollowedToday) return

        // earnDuck이 성공한 경우에만 activity 업데이트
        val success = earnDuck(user)
        if (success) {
            activity.hasFollowedToday = true
            dailyActivityRepository.save(activity)
        }
    }

    private fun earnDuck(user: User): Boolean {
        val selectCollection = selectCollectionRepository.findByUserId(user.id!!)

        if (selectCollection == null) {
            logger.warn("No selected collection found for user: ${user.id}")
            return false
        }

        val collection = collectionRepository.findById(selectCollection.collection.id!!)
            .orElseThrow { IllegalStateException("Collection not found") }

        logger.info("Current duck count: ${collection.duck}")

        if (collection.duck < 7) {
            collection.duck += 1
            collectionRepository.save(collection)
            logger.info("Duck increased to: ${collection.duck} for user: ${user.id}")
            return true
        } else {
            logger.info("Duck already at max (7) for user: ${user.id}")
            return true // 이미 7개라도 성공으로 간주
        }
    }
}
