package building.buck.domain.timeline.persistence

import building.buck.domain.topic.persistence.Topic
import building.buck.global.base.BaseEntity
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "tbl_timeline_item")
class TimelineItem(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timeline_id", nullable = false)
    val timeline: Timeline,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    val topic: Topic,

    @Column(nullable = false)
    val start: LocalDate,

    @Column(nullable = false)
    val end: LocalDate
) : BaseEntity()
