package building.buck.global.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@Configuration
@EnableRedisRepositories(
    basePackages = [
        "building.buck.domain.user.persistence.repository",
        "building.buck.global.security.jwt.refresh.repository",
    ],
)
class RedisConfig
