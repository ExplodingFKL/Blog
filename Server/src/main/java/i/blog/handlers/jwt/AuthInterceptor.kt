package i.blog.handlers.jwt

import i.blog.handlers.exceptions.ExceptionUtils
import org.springframework.stereotype.Component
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 *  token 鉴权
 *
 */
@Component
class AuthInterceptor : HandlerInterceptor {


    @Resource(name = "token")
    private lateinit var tokenService: ITokenService<out Annotation>

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler !is HandlerMethod) {
            return true
        }
        val method = handler.method
        val annotation = tokenService.verifyAnnotation.java
        if (method.isAnnotationPresent(IgnoreToken::class.java) ||
            method.isAnnotationPresent(annotation).not()
        ) {
            return true
        }

        if (tokenService.verifyToken0(
                try {
                    getToken(request)
                } catch (e: Exception) {
                    throw ExceptionUtils.forbidden(request.requestURI)
                },
                method.getAnnotation(annotation)
            )
        ) {
            return true
        } else {
            throw ExceptionUtils.forbidden(request.requestURI)
        }
    }

    companion object {
        private const val headerName = "authorization"
        fun getToken(request: HttpServletRequest): String {
            return request.getHeader(headerName) ?: throw ExceptionUtils.forbidden(request.requestURI)
        }

        fun getHeader(request: WebRequest): String {
            return request.getHeader(headerName) ?: throw ExceptionUtils.forbidden(request.contextPath)
        }
    }
}