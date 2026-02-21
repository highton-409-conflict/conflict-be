package building.buck.domain.timeline.presentation.dto.req

import java.time.LocalDate
import java.util.UUID

data class CreateTimelineItemReq(
    val topicId: UUID,
    val start: LocalDate,
    val end: LocalDate
)
