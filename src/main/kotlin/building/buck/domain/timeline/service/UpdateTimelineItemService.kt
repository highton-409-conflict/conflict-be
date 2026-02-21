package building.buck.domain.timeline.service

import building.buck.domain.timeline.exception.InvalidDateRangeException
import building.buck.domain.timeline.exception.NotTimelineOwnerException
import building.buck.domain.timeline.exception.TimelineItemNotFoundException
import building.buck.domain.timeline.persistence.TimelineItem
import building.buck.domain.timeline.persistence.repository.TimelineItemRepository
import building.buck.domain.timeline.presentation.dto.req.UpdateTimelineItemReq
import building.buck.domain.timeline.presentation.dto.res.TimelineItemRes
import building.buck.domain.topic.exception.TopicNotFoundException
import building.buck.domain.topic.persistence.repository.TopicRepository
import building.buck.domain.user.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class UpdateTimelineItemService(
    private val timelineItemRepository: TimelineItemRepository,
    private val topicRepository: TopicRepository,
    private val userFacade: UserFacade
) {
    @Transactional
    fun execute(itemId: UUID, req: UpdateTimelineItemReq): TimelineItemRes {
        val user = userFacade.currentUser()
        val oldItem = timelineItemRepository.findById(itemId)
            .orElseThrow { TimelineItemNotFoundException }

        if (oldItem.timeline.user.id!! != user.id) throw NotTimelineOwnerException

        // Build new values
        val newTopic = req.topicId?.let {
            topicRepository.findById(it).orElseThrow { TopicNotFoundException }
        } ?: oldItem.topic

        val newStart = req.start ?: oldItem.start
        val newEnd = req.end ?: oldItem.end

        if (newStart.isAfter(newEnd)) throw InvalidDateRangeException

        // Delete old, create new (since fields are immutable)
        timelineItemRepository.delete(oldItem)

        val newItem = timelineItemRepository.save(
            TimelineItem(
                timeline = oldItem.timeline,
                topic = newTopic,
                start = newStart,
                end = newEnd
            )
        )

        return TimelineItemRes(newItem.id!!, newTopic.id!!, newTopic.name, newStart, newEnd)
    }
}
