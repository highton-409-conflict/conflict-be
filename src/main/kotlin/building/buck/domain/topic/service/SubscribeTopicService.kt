package building.buck.domain.topic.service

import building.buck.domain.topic.exception.TopicNotFoundException
import building.buck.domain.topic.persistence.TopicSubscription
import building.buck.domain.topic.persistence.repository.TopicRepository
import building.buck.domain.topic.persistence.repository.TopicSubscriptionRepository
import building.buck.domain.user.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class SubscribeTopicService(
    private val topicRepository: TopicRepository,
    private val topicSubscriptionRepository: TopicSubscriptionRepository,
    private val userFacade: UserFacade
) {
    @Transactional
    fun execute(topicId: UUID) {
        val user = userFacade.currentUser()
        val topic = topicRepository.findById(topicId).orElseThrow { TopicNotFoundException }

        val existing = topicSubscriptionRepository.existsByUserIdAndTopicId(user.id!!, topicId)

        if (existing) {
            // Unsubscribe: find and delete
            val subscription = topicSubscriptionRepository.findAllByUserId(user.id!!)
                .first { it.topic.id!! == topicId }
            topicSubscriptionRepository.delete(subscription)
        } else {
            // Subscribe
            topicSubscriptionRepository.save(
                TopicSubscription(
                    user = user,
                    topic = topic
                )
            )
        }
    }
}
