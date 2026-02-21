package building.buck.domain.timeline.service

import building.buck.domain.timeline.exception.InvalidDateRangeException
import building.buck.domain.timeline.persistence.TimelineItem
import building.buck.domain.timeline.persistence.repository.TimelineItemRepository
import building.buck.domain.timeline.presentation.dto.req.CreateTimelineItemReq
import building.buck.domain.timeline.presentation.dto.res.TimelineItemRes
import building.buck.domain.topic.exception.TopicNotFoundException
import building.buck.domain.topic.persistence.repository.TopicRepository
import building.buck.domain.user.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class CreateTimelineItemService(
    private val timelineItemRepository: TimelineItemRepository,
    private val topicRepository: TopicRepository,
    private val userFacade: UserFacade
) {
    @Transactional
    fun execute(req: CreateTimelineItemReq): TimelineItemRes {
        val user = userFacade.currentUser()
        val timeline = user.timeline ?: throw IllegalStateException("No timeline")

        if (req.start.isAfter(req.end)) throw InvalidDateRangeException

        val topic = topicRepository.findById(req.topicId).orElseThrow { TopicNotFoundException }

        val item = timelineItemRepository.save(
            TimelineItem(
                timeline = timeline,
                topic = topic,
                start = req.start,
                end = req.end
            )
        )

        return TimelineItemRes(item.id!!, topic.id!!, topic.name, item.start, item.end)
    }
}
