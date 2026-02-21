package building.buck.domain.post.service

import building.buck.domain.post.exception.PostNotFoundException
import building.buck.domain.post.persistence.PostLike
import building.buck.domain.post.persistence.repository.PostLikeRepository
import building.buck.domain.post.persistence.repository.PostRepository
import building.buck.domain.post.presentation.dto.res.TogglePostLikeRes
import building.buck.domain.user.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class TogglePostLikeService(
    private val postRepository: PostRepository,
    private val postLikeRepository: PostLikeRepository,
    private val userFacade: UserFacade
) {
    @Transactional
    fun execute(postId: UUID): TogglePostLikeRes {
        val user = userFacade.currentUser()
        val post = postRepository.findById(postId).orElseThrow { PostNotFoundException }

        val existing = postLikeRepository.existsByPostIdAndUserId(postId, user.id!!)

        val isLiked = if (existing) {
            // Unlike: find and delete
            val like = postLikeRepository.findAllByPostId(postId)
                .first { it.user.id!! == user.id }
            postLikeRepository.delete(like)
            false
        } else {
            // Like
            postLikeRepository.save(
                PostLike(post = post, user = user)
            )
            true
        }

        return TogglePostLikeRes(isLiked, postLikeRepository.countByPostId(postId))
    }
}
