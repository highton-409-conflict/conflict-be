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
class GetFollowingsService(
    private val userRepository: UserRepository,
    private val userFollowingRepository: UserFollowingRepository,
    private val timelineRepository: TimelineRepository,
    private val timelineItemRepository: TimelineItemRepository
) {
    fun execute(accountId: String): SearchUsersRes {
        val user = userRepository.findByAccountId(accountId)
            ?: throw UserNotFoundException

        val followings = userFollowingRepository.findAllByFollowerId(user.id!!)
            .map {
                val following = it.following
                val followerCount = userFollowingRepository.countByFollowingId(following.id!!)
                val followingCount = userFollowingRepository.countByFollowerId(following.id!!)

                val timeline = timelineRepository.findByUserId(following.id!!)
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
                    id = following.id!!,
                    accountId = following.accountId,
                    name = following.name,
                    profile = following.profile,
                    followerCount = followerCount,
                    followingCount = followingCount,
                    tags = tags
                )
            }

        return SearchUsersRes(users = followings)
    }
}
