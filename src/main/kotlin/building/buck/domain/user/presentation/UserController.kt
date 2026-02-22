package building.buck.domain.user.presentation

import building.buck.domain.user.facade.UserFacade
import building.buck.domain.user.presentation.dto.req.UpdateProfileReq
import building.buck.domain.user.presentation.dto.res.SearchUsersRes
import building.buck.domain.user.service.FollowUserService
import building.buck.domain.user.service.GetAllUsersService
import building.buck.domain.user.service.GetFollowersService
import building.buck.domain.user.service.GetFollowingsService
import building.buck.domain.user.service.QueryProfileService
import building.buck.domain.user.service.SearchUsersService
import building.buck.domain.user.service.UpdateProfileService
import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@RestController
@RequestMapping("/user")
class UserController(
    private val updateProfileService: UpdateProfileService,
    private val queryProfileService: QueryProfileService,
    private val followUserService: FollowUserService,
    private val getAllUsersService: GetAllUsersService,
    private val getFollowersService: GetFollowersService,
    private val getFollowingsService: GetFollowingsService,
    private val userFacade: UserFacade,
) {
    @Operation(summary = "유저 전체 조회")
    @GetMapping("/all")
    fun getAllUsers(): SearchUsersRes {
        return getAllUsersService.execute()
    }

    @Operation(summary = "프로필 수정")
    @PatchMapping("/profile")
    fun updateProfile(@RequestBody req: UpdateProfileReq) = updateProfileService.execute(req)

    @Operation(summary = "프로필 조회")
    @GetMapping()
    fun queryProfile(@RequestParam accountId: String) = queryProfileService.execute(accountId)

    @Operation(summary = "프로필 조회")
    @GetMapping("/my")
    fun queryMyProfile() = queryProfileService.execute(userFacade.currentUser().accountId)

    @Operation(summary = "유저 팔로우")
    @PostMapping("/{accountId}/follow")
    fun followUser(@PathVariable accountId: String) {
        followUserService.execute(accountId)
    }

    @Operation(summary = "팔로워 목록 조회")
    @GetMapping("/followers")
    fun getFollowers(@RequestParam accountId: String): SearchUsersRes {
        return getFollowersService.execute(accountId)
    }

    @Operation(summary = "팔로잉 목록 조회")
    @GetMapping("/followings")
    fun getFollowings(@RequestParam accountId: String): SearchUsersRes {
        return getFollowingsService.execute(accountId)
    }
}
