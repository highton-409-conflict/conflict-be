package building.buck.domain.post.service

import building.buck.domain.post.exception.NotPostAuthorException
import building.buck.domain.post.exception.PostNotFoundException
import building.buck.domain.post.persistence.Post
import building.buck.domain.post.persistence.PostTag
import building.buck.domain.post.persistence.repository.PostRepository
import building.buck.domain.post.persistence.repository.PostTagRepository
import building.buck.domain.post.presentation.dto.req.UpdatePostReq
import building.buck.domain.post.presentation.dto.res.PostRes
import building.buck.domain.post.presentation.dto.res.UpdatePostRes
import building.buck.domain.topic.exception.TopicNotFoundException
import building.buck.domain.topic.persistence.repository.TopicRepository
import building.buck.domain.user.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class UpdatePostService(
    private val postRepository: PostRepository,
    private val userFacade: UserFacade,
    private val postTagRepository: PostTagRepository,
    private val topicRepository: TopicRepository
) {
    @Transactional
    fun execute(postId: UUID, req: UpdatePostReq): UpdatePostRes {
        val user = userFacade.currentUser()
        val post = postRepository.findById(postId).orElseThrow { PostNotFoundException }

        if (post.author.id!! != user.id) throw NotPostAuthorException
        req.tags.forEach {
            if (!topicRepository.existsById(it)){
                throw TopicNotFoundException
            }
        }
        postTagRepository.deleteAllByPostId(postId)
        req.tags.forEach {
            val topic = topicRepository.findById(it).orElseThrow { TopicNotFoundException }
            postTagRepository.save(PostTag(
                post = post,
                topic = topic,
            ))
        }

        post.title = req.title
        post.content = req.content

        return UpdatePostRes(
            post.id!!,
            post.title,
            post.content,
            post.author.id!!,
            post.createdAt!!,
        )
    }
}
