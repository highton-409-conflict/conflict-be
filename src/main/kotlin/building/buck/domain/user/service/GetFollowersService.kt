package building.buck.domain.user.service

import building.buck.domain.timeline.persistence.repository.TimelineItemRepository
import building.buck.domain.timeline.persistence.repository.TimelineRepository
import building.buck.domain.user.exception.UserNotFoundException
import building.buck.domain.user.persistence.repository.UserFollowingRepository
import building.buck.domain.user.persistence.repository.UserRepository
import building.buck.domain.user.presentation.dto.res.SearchUsersRes
import building.buck.domain.user.presentation.dto.res.TagItem
import building.buck.domain.user.presentation.dto.res.UserItem
import org.springframework.stereotype.Service

@Service
class GetFollowersService(
    private val userRepository: UserRepository,
    private val userFollowingRepository: UserFollowingRepository,
    private val timelineRepository: TimelineRepository,
    private val timelineItemRepository: TimelineItemRepository
) {
    fun execute(accountId: String): SearchUsersRes {
        val user = userRepository.findByAccountId(accountId)
            ?: throw UserNotFoundException

        val followers = userFollowingRepository.findAllByFollowingId(user.id!!)
            .map {
                val follower = it.follower
                val followerCount = userFollowingRepository.countByFollowingId(follower.id!!)
                val followingCount = userFollowingRepository.countByFollowerId(follower.id!!)

                val timeline = timelineRepository.findByUserId(follower.id!!)
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
                    id = follower.id!!,
                    accountId = follower.accountId,
                    name = follower.name,
                    profile = follower.profile,
                    followerCount = followerCount,
                    followingCount = followingCount,
                    tags = tags
                )
            }

        return SearchUsersRes(users = followers)
    }
}
