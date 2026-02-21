package building.buck.domain.post.persistence.repository

import building.buck.domain.post.persistence.PostLike
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PostLikeRepository : JpaRepository<PostLike, UUID> {
    fun findAllByPostId(postId: UUID): List<PostLike>
    fun findAllByUserId(userId: UUID): List<PostLike>
    fun existsByPostIdAndUserId(postId: UUID, userId: UUID): Boolean
    fun countByPostId(postId: UUID): Long
}
