package building.buck.domain.post.service

import building.buck.domain.post.exception.NotPostAuthorException
import building.buck.domain.post.exception.PostNotFoundException
import building.buck.domain.post.exception.TagNotFoundException
import building.buck.domain.post.persistence.repository.PostRepository
import building.buck.domain.post.persistence.repository.PostTagRepository
import building.buck.domain.user.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class RemovePostTagService(
    private val postRepository: PostRepository,
    private val userFacade: UserFacade
) {
    @Transactional
    fun execute(postId: UUID) {
        val user = userFacade.currentUser()
        val post = postRepository.findById(postId).orElseThrow { PostNotFoundException }

        if (post.author.id!! != user.id) throw NotPostAuthorException



        postRepository.delete(post)
    }
}
