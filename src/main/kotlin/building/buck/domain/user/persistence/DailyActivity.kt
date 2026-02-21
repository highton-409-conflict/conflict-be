package building.buck.domain.user.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(value = "daily_activity", timeToLive = 86400) // 24 hours
class DailyActivity(
    @Id
    val userId: String,
    var hasPostedToday: Boolean = false,
    var hasFollowedToday: Boolean = false,
)
