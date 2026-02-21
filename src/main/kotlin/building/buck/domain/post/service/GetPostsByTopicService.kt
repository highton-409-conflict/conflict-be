package building.buck.domain.post.service

import building.buck.domain.post.persistence.repository.PostLikeRepository
import building.buck.domain.post.persistence.repository.PostTagRepository
import building.buck.domain.post.presentation.dto.res.PostRes
import building.buck.domain.post.presentation.dto.res.TagRes
import building.buck.domain.user.facade.UserFacade
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GetPostsByTopicService(
    private val postTagRepository: PostTagRepository,
    private val postLikeRepository: PostLikeRepository,
    private val userFacade: UserFacade
) {
    fun execute(topicId: UUID): List<PostRes> {
        val user = userFacade.currentUser()

        val postTags = postTagRepository.findAllByTopicId(topicId)
        val posts = postTags.map { it.post }.distinct()

        return posts.map { post ->
            PostRes(
                id = post.id!!,
                title = post.title,
                content = post.content,
                authorId = post.author.id!!,
                createdAt = post.createdAt!!,
                likes = postLikeRepository.countByPostId(post.id!!),
                tags = postTagRepository.findAllByPostId(post.id!!).map {
                    TagRes(it.topic.id!!, it.topic.name)
                },
                isLiked = postLikeRepository.existsByPostIdAndUserId(post.id!!, user.id!!)
            )
        }
    }
}
