package building.buck.infra.s3

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import dsm.pick2024.infrastructure.s3.exception.BadFileExtensionException
import dsm.pick2024.infrastructure.s3.exception.EmptyFileException
import org.aspectj.weaver.tools.cache.SimpleCacheFactory.path
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.*

@Component
class FileUtil(
    private val amazonS3: AmazonS3
) {
    @Value("\${cloud.aws.s3.bucket}")
    lateinit var bucketName: String

    @Value("\${cloud.aws.s3.exp-time}")
    private lateinit var s3Exp: String

    fun upload(file: MultipartFile): String {
        val ext = verificationFile(file)

        val randomName = UUID.randomUUID().toString()
        val filename = "$randomName.$ext"
        val inputStream: InputStream = ByteArrayInputStream(file.bytes)

        val metadata = ObjectMetadata().apply {
            contentType = MediaType.IMAGE_PNG_VALUE
            contentLength = file.size
            contentDisposition = "inline"
        }

        inputStream.use {
            amazonS3.putObject(
                PutObjectRequest(bucketName, "$filename", it, metadata)
                    .withCannedAcl(CannedAccessControlList.AuthenticatedRead)
            )
        }

        return "https://building-duck.s3.us-east-1.amazonaws.com/"+filename
    }

    fun delete(objectName: String) {
        amazonS3.deleteObject(bucketName, path + objectName)
    }

    fun generateObjectUrl(fileName: String): String {
        val expiration = Date().apply {
            time += s3Exp.toLong()
        }

        return amazonS3.generatePresignedUrl(
            GeneratePresignedUrlRequest(
                bucketName,
                fileName
            ).withMethod(HttpMethod.GET).withExpiration(expiration)
        ).toString()
    }

    private fun verificationFile(file: MultipartFile): String {
        if (file.isEmpty || file.originalFilename == null) throw EmptyFileException
        val originalFilename = file.originalFilename!!
        val ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).lowercase(Locale.getDefault())

        if (!(ext == "jpg" || ext == "jpeg" || ext == "png" || ext == "heic" || ext == "hwp" || ext == "pptx")) {
            throw BadFileExtensionException
        }

        return ext
    }
}
