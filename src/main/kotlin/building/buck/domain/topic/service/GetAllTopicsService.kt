package building.buck.domain.topic.service

import building.buck.domain.topic.persistence.repository.TopicRepository
import building.buck.domain.topic.presentation.dto.res.TopicRes
import org.springframework.stereotype.Service

@Service
class GetAllTopicsService(
    private val topicRepository: TopicRepository
) {
    fun execute(): List<TopicRes> {
        return topicRepository.findAll().map {
            TopicRes(
                id = it.id!!,
                name = it.name,
                parentId = it.parent?.id,
                image = it.image
            )
        }
    }
}
