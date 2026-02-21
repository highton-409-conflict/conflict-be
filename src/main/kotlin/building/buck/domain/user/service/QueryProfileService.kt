package building.buck.domain.user.service

import building.buck.domain.collection.persistence.repository.CollectionRepository
import building.buck.domain.collection.persistence.repository.SelectCollectionRepository
import building.buck.domain.timeline.exception.TimelineItemNotFoundException
import building.buck.domain.timeline.persistence.repository.TimelineItemRepository
import building.buck.domain.timeline.persistence.repository.TimelineRepository
import building.buck.domain.user.exception.UserNotFoundException
import building.buck.domain.user.facade.UserFacade
import building.buck.domain.user.persistence.repository.UserFollowingRepository
import building.buck.domain.user.persistence.repository.UserRepository
import building.buck.domain.user.presentation.dto.res.QueryProfileRes
import building.buck.domain.user.presentation.dto.res.TimelineTopic
import org.springframework.stereotype.Service
import java.util.*

@Service
class QueryProfileService(
    private val userRepository: UserRepository,
    private val timelineRepository: TimelineRepository,
    private val timelineItemRepository: TimelineItemRepository,
    private val selectCollectionRepository: SelectCollectionRepository,
    private val collectionRepository: CollectionRepository,
    private val userFollowingRepository: UserFollowingRepository
) {
    fun execute(userId: UUID): QueryProfileRes{
        val user = userRepository.findById(userId).orElseThrow{UserNotFoundException}
        val timeline = timelineRepository.findByUserId(user.id!!) ?: throw TimelineItemNotFoundException
        val timelineItems = timelineItemRepository.findAllByTimelineId(timeline.id!!)
        val selectCollection = selectCollectionRepository.findByUserId(user.id!!)
        val userCollection = selectCollection?.collection?.let { collectionRepository.findById(it.id!!).orElseThrow() }

        return QueryProfileRes(
            name = user.name,
            accountId = user.accountId,
            introduce = user.introduce,
            profileUrl = user.profile,
            collectionUrl = userCollection?.image,
            following = userFollowingRepository.countByFollowerId(user.id!!),
            followers = userFollowingRepository.countByFollowingId(user.id!!),
            timeline = timelineItems.map {
                TimelineTopic(
                    topicId = it.topic.id!!,
                    topicName = it.topic.name,
                    startDate = it.start,
                    endDate = it.end,
                    topicImageUrl = it.topic.image
                )
            }
        )
    }
}