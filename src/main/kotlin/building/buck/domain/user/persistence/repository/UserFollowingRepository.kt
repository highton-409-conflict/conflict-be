package building.buck.domain.user.persistence.repository

import building.buck.domain.user.persistence.UserFollowing
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserFollowingRepository : JpaRepository<UserFollowing, UUID> {
    fun findAllByFollowerId(followerId: UUID): List<UserFollowing>
    fun findAllByFollowingId(followingId: UUID): List<UserFollowing>
    fun existsByFollowerIdAndFollowingId(followerId: UUID, followingId: UUID): Boolean
    fun countByFollowingId(followingId: UUID): Int
    fun countByFollowerId(followerId: UUID): Int
}
