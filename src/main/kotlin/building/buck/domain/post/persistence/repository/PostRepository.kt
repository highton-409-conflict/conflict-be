package building.buck.domain.post.persistence.repository

import building.buck.domain.post.persistence.Post
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PostRepository : JpaRepository<Post, UUID> {
    fun findAllByAuthorId(authorId: UUID): List<Post>
}
