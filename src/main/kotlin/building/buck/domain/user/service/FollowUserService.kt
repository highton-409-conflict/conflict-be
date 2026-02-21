package building.buck.domain.user.service

import building.buck.domain.user.exception.CannotFollowSelfException
import building.buck.domain.user.exception.UserNotFoundException
import building.buck.domain.user.facade.UserFacade
import building.buck.domain.user.persistence.UserFollowing
import building.buck.domain.user.persistence.repository.UserFollowingRepository
import building.buck.domain.user.persistence.repository.UserRepository
import building.buck.domain.user.service.internal.DuckEarningService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class FollowUserService(
    private val userFollowingRepository: UserFollowingRepository,
    private val userRepository: UserRepository,
    private val userFacade: UserFacade,
    private val duckEarningService: DuckEarningService
) {
    @Transactional
    fun execute(userId: UUID) {
        val follower = userFacade.currentUser()

        if (follower.id!! == userId) throw CannotFollowSelfException

        val following = userRepository.findById(userId)
            .orElseThrow { UserNotFoundException }

        val existing = userFollowingRepository.existsByFollowerIdAndFollowingId(follower.id!!, userId)

        if (existing) {
            // Unfollow: find and delete
            val relationship = userFollowingRepository.findAllByFollowerId(follower.id!!)
                .first { it.following.id!! == userId }
            userFollowingRepository.delete(relationship)
        } else {
            // Follow
            userFollowingRepository.save(
                UserFollowing(
                    follower = follower,
                    following = following
                )
            )

            // Earn duck (daily limit handled in DuckEarningService)
            duckEarningService.earnDuckForFollow(follower)
        }
    }
}
