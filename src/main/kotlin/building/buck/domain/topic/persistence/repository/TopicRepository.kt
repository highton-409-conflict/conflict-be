package building.buck.domain.topic.persistence.repository

import building.buck.domain.topic.persistence.Topic
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TopicRepository : JpaRepository<Topic, UUID> {
    fun findAllByParentId(parentId: UUID?): List<Topic>
}
