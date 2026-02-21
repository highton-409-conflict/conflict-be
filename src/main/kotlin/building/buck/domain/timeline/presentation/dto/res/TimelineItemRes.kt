package building.buck.domain.timeline.presentation.dto.res

import java.time.LocalDate
import java.util.UUID

data class TimelineItemRes(
    val id: UUID,
    val topicId: UUID,
    val topicName: String,
    val start: LocalDate,
    val end: LocalDate
)
