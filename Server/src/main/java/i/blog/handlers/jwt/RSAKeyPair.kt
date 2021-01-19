package i.blog.handlers.jwt

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
data class RSAKeyPair(
    @Value("\${app.security.publicKey}")
    val publicKey: String,
    @Value("\${app.security.privateKey}")
    val privateKey: String
)