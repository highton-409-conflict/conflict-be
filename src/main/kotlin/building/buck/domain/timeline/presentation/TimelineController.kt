package building.buck.domain.timeline.presentation

import building.buck.domain.timeline.presentation.dto.req.CreateTimelineItemReq
import building.buck.domain.timeline.presentation.dto.req.UpdateTimelineItemReq
import building.buck.domain.timeline.presentation.dto.res.TimelineItemRes
import building.buck.domain.timeline.service.CreateTimelineItemService
import building.buck.domain.timeline.service.DeleteTimelineItemService
import building.buck.domain.timeline.service.UpdateTimelineItemService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/timelines")
class TimelineController(
    private val createTimelineItemService: CreateTimelineItemService,
    private val updateTimelineItemService: UpdateTimelineItemService,
    private val deleteTimelineItemService: DeleteTimelineItemService
) {
    @Operation(summary = "타임라인 아이템 생성")
    @PostMapping("/items")
    fun createTimelineItem(@RequestBody req: CreateTimelineItemReq): TimelineItemRes {
        return createTimelineItemService.execute(req)
    }

    @Operation(summary = "타임라인 아이템 수정")
    @PatchMapping("/items/{itemId}")
    fun updateTimelineItem(
        @PathVariable itemId: UUID,
        @RequestBody req: UpdateTimelineItemReq
    ): TimelineItemRes {
        return updateTimelineItemService.execute(itemId, req)
    }

    @Operation(summary = "타임라인 아이템 삭제")
    @DeleteMapping("/items/{itemId}")
    fun deleteTimelineItem(@PathVariable itemId: UUID) {
        deleteTimelineItemService.execute(itemId)
    }
}
