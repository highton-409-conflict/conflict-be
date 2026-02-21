package building.buck.global.security.jwt.refresh.repository

import building.buck.global.security.jwt.refresh.RefreshToken
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository : CrudRepository<RefreshToken, String> {
    fun findByToken(token: String): RefreshToken?
}
