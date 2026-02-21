package building.buck.domain.topic.service

import building.buck.domain.topic.exception.TopicNotFoundException
import building.buck.domain.topic.persistence.Topic
import building.buck.domain.topic.persistence.repository.TopicRepository
import building.buck.domain.topic.presentation.dto.req.CreateTopicReq
import building.buck.domain.topic.presentation.dto.res.TopicRes
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class CreateTopicService(
    private val topicRepository: TopicRepository
) {
    @Transactional
    fun execute(req: CreateTopicReq): TopicRes {
        val parent = req.parentId?.let {
            topicRepository.findById(it).orElseThrow { TopicNotFoundException }
        }

        val topic = topicRepository.save(
            Topic(
                name = req.name,
                parent = parent,
                image = req.image
            )
        )

        return TopicRes(
            id = topic.id!!,
            name = topic.name,
            parentId = parent?.id,
            image = topic.image
        )
    }
}
