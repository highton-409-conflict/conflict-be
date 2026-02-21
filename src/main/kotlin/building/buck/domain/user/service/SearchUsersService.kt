package building.buck.domain.user.service

import building.buck.domain.timeline.persistence.repository.TimelineItemRepository
import building.buck.domain.timeline.persistence.repository.TimelineRepository
import building.buck.domain.topic.persistence.repository.TopicSubscriptionRepository
import building.buck.domain.user.persistence.repository.UserFollowingRepository
import building.buck.domain.user.persistence.repository.UserRepository
import building.buck.domain.user.presentation.dto.res.SearchUsersRes
import building.buck.domain.user.presentation.dto.res.TagItem
import building.buck.domain.user.presentation.dto.res.UserItem
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class SearchUsersService(
    private val userRepository: UserRepository,
    private val topicSubscriptionRepository: TopicSubscriptionRepository,
    private val userFollowingRepository: UserFollowingRepository,
    private val timelineRepository: TimelineRepository,
    private val timelineItemRepository: TimelineItemRepository
) {
    fun execute(id: String?, tags: List<UUID>?): SearchUsersRes {
        var users = userRepository.findAll()

        // Filter by id (partial match)
        id?.let { keyword ->
            users = users.filter {
                it.accountId.contains(keyword, ignoreCase = true) ||
                it.name.contains(keyword, ignoreCase = true)
            }
        }

        // Filter by subscribed topics
        tags?.let { topicIds ->
            users = users.filter { user ->
                val subscriptions = topicSubscriptionRepository.findAllByUserId(user.id!!)
                subscriptions.any { it.topic.id!! in topicIds }
            }
        }

        return SearchUsersRes(users.map { user ->
            val followerCount = userFollowingRepository.countByFollowingId(user.id!!)
            val followingCount = userFollowingRepository.countByFollowerId(user.id!!)

            val timeline = timelineRepository.findByUserId(user.id!!)
            val timelineTags = timeline?.let { tl ->
                timelineItemRepository.findAllByTimelineId(tl.id!!)
                    .map { item ->
                        TagItem(
                            id = item.topic.id!!,
                            name = item.topic.name
                        )
                    }
            } ?: emptyList()

            UserItem(
                id = user.id!!,
                accountId = user.accountId,
                name = user.name,
                profile = user.profile,
                followerCount = followerCount,
                followingCount = followingCount,
                tags = timelineTags
            )
        })
    }
}
