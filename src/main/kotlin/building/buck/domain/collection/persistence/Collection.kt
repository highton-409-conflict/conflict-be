package building.buck.domain.collection.persistence

import building.buck.domain.user.persistence.User
import building.buck.global.base.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "tbl_collection")
class Collection(
    @Column(nullable = false)
    var duck: Int,

    @Column
    var image: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User
) : BaseEntity()
