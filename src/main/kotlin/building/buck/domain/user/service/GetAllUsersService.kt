package building.buck.domain.user.service

import building.buck.domain.timeline.persistence.repository.TimelineItemRepository
import building.buck.domain.timeline.persistence.repository.TimelineRepository
import building.buck.domain.user.persistence.repository.UserFollowingRepository
import building.buck.domain.user.persistence.repository.UserRepository
import building.buck.domain.user.presentation.dto.res.SearchUsersRes
import building.buck.domain.user.presentation.dto.res.TagItem
import building.buck.domain.user.presentation.dto.res.UserItem
import org.springframework.stereotype.Service

@Service
class GetAllUsersService(
    private val userRepository: UserRepository,
    private val userFollowingRepository: UserFollowingRepository,
    private val timelineRepository: TimelineRepository,
    private val timelineItemRepository: TimelineItemRepository
) {
    fun execute(): SearchUsersRes {
        val users = userRepository.findAll().map { user ->
            val followerCount = userFollowingRepository.countByFollowingId(user.id!!)
            val followingCount = userFollowingRepository.countByFollowerId(user.id!!)

            val timeline = timelineRepository.findByUserId(user.id!!)
            val tags = timeline?.let { tl ->
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
                tags = tags
            )
        }

        return SearchUsersRes(users = users)
    }
}
