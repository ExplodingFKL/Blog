package i.blog.modules.users.services.impl

import com.github.openEdgn.logger4k.getLogger
import i.blog.handlers.jwt.ITokenService
import i.blog.modules.users.models.annotations.Token
import org.springframework.stereotype.Service

@Service("token")
class TokenServiceImpl : ITokenService<Token> {
    override val logger = getLogger()

    override fun getUserIdByToken(token: String): Long {
        return 0
    }

    override fun verifyToken(token: String, kClass: Token): Boolean {
        return true
    }

    override val verifyAnnotation = Token::class
}