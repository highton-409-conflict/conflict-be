package building.buck.infra.s3.presentation

import building.buck.infra.s3.FileUtil
import building.buck.infra.s3.presentation.dto.res.ImageUploadRes
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/file")
class FileController(
    private val fileUtil: FileUtil
) {
    @Operation(summary = "이미지 업로드")
    @PostMapping("/image")
    fun uploadImage(@RequestParam("image") file: MultipartFile): ImageUploadRes {
        val url = fileUtil.upload(file)
        return ImageUploadRes(url = url)
    }
}
