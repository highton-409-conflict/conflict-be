package building.buck.domain.notification.presentation

import building.buck.domain.notification.presentation.dto.res.NotificationsRes
import building.buck.domain.notification.service.QueryNotificationsService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/notification")
class NotificationController(
    private val queryNotificationsService: QueryNotificationsService
) {
    @Operation(summary = "알림 목록 조회")
    @GetMapping
    fun queryNotifications(): NotificationsRes {
        return queryNotificationsService.execute()
    }
}
