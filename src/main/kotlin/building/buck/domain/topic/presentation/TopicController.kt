package building.buck.domain.topic.presentation

import building.buck.domain.topic.presentation.dto.req.CreateTopicReq
import building.buck.domain.topic.presentation.dto.res.TopicRes
import building.buck.domain.topic.service.CreateTopicService
import building.buck.domain.topic.service.GetAllTopicsService
import building.buck.domain.topic.service.SubscribeTopicService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/topics")
class TopicController(
    private val createTopicService: CreateTopicService,
    private val subscribeTopicService: SubscribeTopicService,
    private val getAllTopicsService: GetAllTopicsService
) {
    @Operation(summary = "토픽 전체 조회")
    @GetMapping
    fun getAllTopics(): List<TopicRes> {
        return getAllTopicsService.execute()
    }

    @Operation(summary = "토픽 생성")
    @PostMapping
    fun createTopic(@RequestBody req: CreateTopicReq): TopicRes {
        return createTopicService.execute(req)
    }

    @Operation(summary = "토픽 구독")
    @PostMapping("/{topicId}/subscribe")
    fun subscribeTopic(@PathVariable topicId: UUID) {
        subscribeTopicService.execute(topicId)
    }
}
