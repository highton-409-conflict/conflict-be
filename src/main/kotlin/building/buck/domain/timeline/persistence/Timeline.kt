package building.buck.domain.timeline.persistence

import building.buck.domain.user.persistence.User
import building.buck.global.base.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "tbl_timeline")
class Timeline(
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User
) : BaseEntity() {

    @OneToMany(mappedBy = "timeline", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    var timelineItems: MutableList<TimelineItem> = mutableListOf()
}
