package i.blog.handlers.resolver

import i.blog.handlers.jwt.AuthInterceptor
import i.blog.handlers.jwt.ITokenService
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import javax.annotation.Resource


/**
 * JWT 注入用户信息
 */
@Component
class UserIdArgumentResolver : HandlerMethodArgumentResolver {


    @Resource(name = "token")
    private lateinit var tokenService: ITokenService<out Annotation>

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return (parameter.parameterType.isAssignableFrom(Long::class.java) ||
                parameter.parameterType.isAssignableFrom(Long::class.javaObjectType)) &&
                parameter.hasParameterAnnotation(UserId::class.java)
    }


    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Long {
        return tokenService.getUserIdByToken(AuthInterceptor.getHeader(webRequest))
    }
}