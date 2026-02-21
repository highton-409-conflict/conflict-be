package building.buck.domain.user.persistence

import building.buck.global.base.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "tbl_user_following")
class UserFollowing(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id", nullable = false)
    val follower: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id", nullable = false)
    val following: User
) : BaseEntity()
