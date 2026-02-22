package building.buck.domain.post.service

import building.buck.domain.post.persistence.repository.PostLikeRepository
import building.buck.domain.post.persistence.repository.PostRepository
import building.buck.domain.post.persistence.repository.PostTagRepository
import building.buck.domain.post.presentation.dto.res.PostRes
import building.buck.domain.post.presentation.dto.res.TagRes
import building.buck.domain.user.facade.UserFacade
import org.springframework.stereotype.Service

@Service
class GetAllPostsService(
    private val postRepository: PostRepository,
    private val postLikeRepository: PostLikeRepository,
    private val postTagRepository: PostTagRepository,
    private val userFacade: UserFacade
) {
    fun execute(): List<PostRes> {
        val user = userFacade.currentUser()
        return postRepository.findAll().map {
            PostRes(
                id = it.id!!,
                title = it.title,
                content = it.content,
                authorId = it.author.accountId,
                createdAt = it.createdAt!!,
                likes = postLikeRepository.countByPostId(it.id!!),
                tags = postTagRepository.findAllByPostId(it.id!!).map { TagRes(it.topic.id!!,it.topic.name) },
                isLiked = postLikeRepository.existsByPostIdAndUserId(it.id!!, user.id!!)

            )
        }
    }
}
