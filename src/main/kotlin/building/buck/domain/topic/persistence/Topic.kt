package building.buck.domain.topic.persistence

import building.buck.domain.timeline.persistence.TimelineItem
import building.buck.global.base.BaseEntity
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "tbl_topic")
class Topic(
    @Column(nullable = false)
    val name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    val parent: Topic? = null,

    @Column
    val image: String? = null
) : BaseEntity() {

    @OneToMany(mappedBy = "parent", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    var children: MutableList<Topic> = mutableListOf()

    @OneToMany(mappedBy = "topic", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    var timelineItems: MutableList<TimelineItem> = mutableListOf()

    @OneToMany(mappedBy = "topic", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    var subscriptions: MutableList<TopicSubscription> = mutableListOf()
}
