package i.design.handlers

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@OpenAPIDefinition(
    info =
    Info(title = "在线问卷调查系统", version = "1.0", description = "在线问卷调查系统 API 文档 v1.0")
)
@Configuration
class SpringDocConfig {
    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .components(
                Components()
                    .addSecuritySchemes(
                        "auth",
                        SecurityScheme()
                            .name("注册用户支持")
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer").bearerFormat("JWT")
                    )
            )
    }
//
//    @Bean
//    fun customOpenAPI1(): OpenAPI? {
//        val securitySchemeName = "bearerAuth"
//        val apiTitle = String.format("%s API", "")
//        return OpenAPI()
//            .addSecurityItem(SecurityRequirement().addList(securitySchemeName))
//            .components(
//                Components()
//                    .addSecuritySchemes(
//                        securitySchemeName,
//                        SecurityScheme()
//                            .name(securitySchemeName)
//                            .type(SecurityScheme.Type.HTTP)
//                            .scheme("bearer")
//                            .bearerFormat("JWT")
//                    )
//            )
//            .info(Info().title(apiTitle).version("1.0"))
//    }
}