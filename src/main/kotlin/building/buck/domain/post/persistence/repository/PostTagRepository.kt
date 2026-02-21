package building.buck.domain.post.persistence.repository

import building.buck.domain.post.persistence.PostTag
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PostTagRepository : JpaRepository<PostTag, UUID> {
    fun findAllByPostId(postId: UUID): List<PostTag>
    fun findAllByTopicId(topicId: UUID): List<PostTag>
    fun deleteAllByPostId(postId: UUID)
}
