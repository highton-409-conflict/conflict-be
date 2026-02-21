package building.buck.domain.user.presentation

import building.buck.domain.user.presentation.dto.req.LoginReq
import building.buck.domain.user.presentation.dto.req.SignUpReq
import building.buck.domain.user.presentation.dto.req.UpdateProfileReq
import building.buck.domain.user.service.LoginService
import building.buck.domain.user.service.RefreshTokenService
import building.buck.domain.user.service.SignUpService
import building.buck.domain.user.service.UpdateProfileService
import building.buck.domain.user.service.WithdrawService
import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/auth")
class AuthController(
    private val loginService: LoginService,
    private val signUpService: SignUpService,
    private val refreshTokenService: RefreshTokenService,
    private val withdrawService: WithdrawService,
    private val objectMapper: ObjectMapper,
) {
    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    fun signUp(@RequestBody req: SignUpReq) = signUpService.execute(req)

    @Operation(summary = "로그인")
    @PostMapping("/login")
    fun login(@RequestBody req: LoginReq) = loginService.execute(req)

    @Operation(summary = "유저 토큰 재발급 API")
    @PutMapping("/refresh")
    fun userTokenRefresh(
        @RequestHeader("X-Refresh-Token") token: String
    ) = refreshTokenService.execute(token)

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("/withdraw")
    fun withdraw() {
        withdrawService.execute()
    }
}
