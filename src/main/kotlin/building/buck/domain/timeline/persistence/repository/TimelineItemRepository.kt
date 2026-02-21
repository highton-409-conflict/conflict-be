package building.buck.domain.timeline.persistence.repository

import building.buck.domain.timeline.persistence.TimelineItem
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TimelineItemRepository : JpaRepository<TimelineItem, UUID> {
    fun findAllByTimelineId(timelineId: UUID): List<TimelineItem>
}
