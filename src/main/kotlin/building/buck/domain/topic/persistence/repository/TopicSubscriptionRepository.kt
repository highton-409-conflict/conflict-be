package building.buck.domain.topic.persistence.repository

import building.buck.domain.topic.persistence.TopicSubscription
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TopicSubscriptionRepository : JpaRepository<TopicSubscription, UUID> {
    fun findAllByUserId(userId: UUID): List<TopicSubscription>
    fun findAllByTopicId(topicId: UUID): List<TopicSubscription>
    fun existsByUserIdAndTopicId(userId: UUID, topicId: UUID): Boolean
}
