package building.buck.domain.post.service

import building.buck.domain.notification.persistence.Notification
import building.buck.domain.notification.persistence.NotificationType
import building.buck.domain.notification.persistence.UserNotification
import building.buck.domain.notification.persistence.repository.NotificationRepository
import building.buck.domain.notification.persistence.repository.UserNotificationRepository
import building.buck.domain.post.persistence.Post
import building.buck.domain.post.persistence.PostTag
import building.buck.domain.post.persistence.repository.PostRepository
import building.buck.domain.post.persistence.repository.PostTagRepository
import building.buck.domain.post.presentation.dto.req.CreatePostReq
import building.buck.domain.post.presentation.dto.res.PostRes
import building.buck.domain.post.presentation.dto.res.UpdatePostRes
import building.buck.domain.topic.persistence.repository.TopicRepository
import building.buck.domain.topic.persistence.repository.TopicSubscriptionRepository
import building.buck.domain.user.facade.UserFacade
import building.buck.domain.user.persistence.User
import building.buck.domain.user.persistence.repository.UserFollowingRepository
import building.buck.domain.user.service.internal.DuckEarningService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class CreatePostService(
    private val postRepository: PostRepository,
    private val postTagRepository: PostTagRepository,
    private val topicRepository: TopicRepository,
    private val userFacade: UserFacade,
    private val duckEarningService: DuckEarningService,
    private val topicSubscriptionRepository: TopicSubscriptionRepository,
    private val userFollowingRepository: UserFollowingRepository,
    private val notificationRepository: NotificationRepository,
    private val userNotificationRepository: UserNotificationRepository
) {
    @Transactional
    fun execute(req: CreatePostReq): UpdatePostRes {
        val author = userFacade.currentUser()

        val post = postRepository.save(
            Post(
                title = req.title,
                content = req.content,
                author = author
            )
        )

        // Add tags if provided
        val taggedTopics = mutableListOf<UUID>()
        req.tags?.forEach { topicId ->
            val topic = topicRepository.findById(topicId).orElse(null)
            topic?.let {
                postTagRepository.save(
                    PostTag(post = post, topic = it)
                )
                taggedTopics.add(topicId)
            }
        }

        duckEarningService.earnDuckForPost(author)

        // Create notifications
        createNotifications(post, author, taggedTopics)

        return UpdatePostRes(post.id!!, post.title, post.content, author.id!!, post.createdAt!!)
    }

    private fun createNotifications(post: Post, author: User, topicIds: List<UUID>) {
        val notificationReceivers = mutableSetOf<User>()

        // 1. 토픽 구독자들 찾기
        topicIds.forEach { topicId ->
            val subscribers = topicSubscriptionRepository.findAllByTopicId(topicId)
            subscribers.forEach { subscription ->
                if (subscription.user.id != author.id) {
                    notificationReceivers.add(subscription.user)
                }
            }
        }

        // 2. 작성자를 팔로잉하는 사람들 찾기
        val followers = userFollowingRepository.findAllByFollowingId(author.id!!)
        followers.forEach { following ->
            if (following.follower.id != author.id) {
                notificationReceivers.add(following.follower)
            }
        }

        // 3. 알림이 없으면 종료
        if (notificationReceivers.isEmpty()) return

        // 4. Notification 생성
        val notification = notificationRepository.save(
            Notification(
                title = "새 게시글",
                body = "${author.name}님이 새 게시글을 작성했습니다: ${post.title}",
                type = NotificationType.POST,
                link = post.id!!
            )
        )

        // 5. 각 사용자에게 UserNotification 생성
        notificationReceivers.forEach { user ->
            userNotificationRepository.save(
                UserNotification(
                    user = user,
                    notification = notification,
                )
            )
        }
    }
}
