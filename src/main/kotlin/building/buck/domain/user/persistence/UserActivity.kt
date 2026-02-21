package building.buck.domain.user.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(value = "activity")
class UserActivity (
    @Id
    val accountId: String
)