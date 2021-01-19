package i.blog.handlers.jwt

import com.github.openEdgn.logger4k.ILogger
import kotlin.reflect.KClass

interface ITokenService<T : Annotation> {
    val logger: ILogger

    fun getUserIdByToken(token: String): Long
    @Suppress("UNCHECKED_CAST")
    fun verifyToken0(token: String, tokenInfo: Any): Boolean {
        return try {
            verifyToken(token, tokenInfo as T)
        } catch (e: Exception) {
            logger.warn("token 解析错误！ {}", e.message)
            false
        }
    }

    fun verifyToken(token: String, kClass: T): Boolean

    val verifyAnnotation: KClass<T>
}