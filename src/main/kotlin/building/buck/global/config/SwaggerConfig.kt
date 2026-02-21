package building.buck.global.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.v3.core.jackson.ModelResolver
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig(
    private val objectMapper: ObjectMapper
) {
    @Bean
    fun openAPI(): OpenAPI {
        val securitySchemeName = "bearerAuth"
        val securityRequirement = SecurityRequirement().addList(securitySchemeName)
        val components = Components()
            .addSecuritySchemes(
                securitySchemeName,
                SecurityScheme()
                    .name(securitySchemeName)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
            )

        return OpenAPI()
            .components(components)
            .info(apiInfo())
            .addSecurityItem(securityRequirement)
    }

    @Bean
    fun modelResolver(): ModelResolver {
        return ModelResolver(objectMapper)
    }

    private fun apiInfo(): Info {
        return Info()
            .title("Building Duck API")
            .description("Building Duck Backend API Documentation")
            .version("1.0.0")
    }
}
