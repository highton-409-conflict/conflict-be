package building.buck.domain.post.presentation

import building.buck.domain.post.presentation.dto.req.CreatePostReq
import building.buck.domain.post.presentation.dto.req.UpdatePostReq
import building.buck.domain.post.presentation.dto.res.PostRes
import building.buck.domain.post.presentation.dto.res.TogglePostLikeRes
import building.buck.domain.post.presentation.dto.res.UpdatePostRes
import building.buck.domain.post.service.CreatePostService
import building.buck.domain.post.service.GetAllPostsService
import building.buck.domain.post.service.GetPostsByTopicService
import building.buck.domain.post.service.RemovePostTagService
import building.buck.domain.post.service.TogglePostLikeService
import building.buck.domain.post.service.UpdatePostService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/posts")
class PostController(
    private val createPostService: CreatePostService,
    private val updatePostService: UpdatePostService,
    private val removePostTagService: RemovePostTagService,
    private val togglePostLikeService: TogglePostLikeService,
    private val getAllPostsService: GetAllPostsService,
    private val getPostsByTopicService: GetPostsByTopicService
) {
    @Operation(summary = "게시글 전체 조회")
    @GetMapping
    fun getAllPosts(): List<PostRes> {
        return getAllPostsService.execute()
    }

    @Operation(summary = "토픽별 게시글 조회")
    @GetMapping("/topic")
    fun getPostsByTopic(@RequestParam topicId: UUID): List<PostRes> {
        return getPostsByTopicService.execute(topicId)
    }

    @Operation(summary = "게시글 생성")
    @PostMapping
    fun createPost(@RequestBody req: CreatePostReq): UpdatePostRes {
        return createPostService.execute(req)
    }

    @Operation(summary = "게시글 수정")
    @PatchMapping("/{postId}")
    fun updatePost(@PathVariable postId: UUID, @RequestBody req: UpdatePostReq): UpdatePostRes {
        return updatePostService.execute(postId, req)
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{postId}")
    fun removePostTag(@PathVariable postId: UUID) {
        removePostTagService.execute(postId)
    }

    @Operation(summary = "게시글 좋아요 토글")
    @PostMapping("/{postId}/like")
    fun togglePostLike(@PathVariable postId: UUID): TogglePostLikeRes {
        return togglePostLikeService.execute(postId)
    }
}
