package building.buck.domain.timeline.service

import building.buck.domain.timeline.exception.NotTimelineOwnerException
import building.buck.domain.timeline.exception.TimelineItemNotFoundException
import building.buck.domain.timeline.persistence.repository.TimelineItemRepository
import building.buck.domain.user.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class DeleteTimelineItemService(
    private val timelineItemRepository: TimelineItemRepository,
    private val userFacade: UserFacade
) {
    @Transactional
    fun execute(itemId: UUID) {
        val user = userFacade.currentUser()
        val item = timelineItemRepository.findById(itemId)
            .orElseThrow { TimelineItemNotFoundException }

        if (item.timeline.user.id!! != user.id) throw NotTimelineOwnerException

        timelineItemRepository.delete(item)
    }
}
