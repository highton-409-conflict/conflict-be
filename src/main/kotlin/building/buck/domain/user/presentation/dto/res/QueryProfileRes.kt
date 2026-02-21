package building.buck.domain.user.presentation.dto.res

import java.time.LocalDate
import java.util.UUID

data class QueryProfileRes(
    val name: String,
    val accountId: String,
    val introduce: String?,
    val profileUrl: String?,
    val collectionUrl: String?,
    val followers: Int,
    val following: Int,
    val timeline: List<TimelineTopic>
)

data class TimelineTopic(
    val topicId: UUID,
    val topicName: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val topicImageUrl: String?,
)
