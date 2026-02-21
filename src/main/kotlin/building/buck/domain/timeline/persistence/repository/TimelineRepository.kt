package building.buck.domain.timeline.persistence.repository

import building.buck.domain.timeline.persistence.Timeline
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TimelineRepository : JpaRepository<Timeline, UUID> {
    fun findByUserId(userId: UUID): Timeline?
}
