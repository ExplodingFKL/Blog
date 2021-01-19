package i.blog.handlers.resolver

import i.blog.handlers.exceptions.ExceptionUtils
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
 * 根据Token注入用户Id
 */
@Component
class UserIdArgumentResolver : HandlerMethodArgumentResolver {


    @Resource(name = "token")
    private lateinit var tokenService: ITokenService<out Annotation>

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        if (parameter.method?.isAnnotationPresent(tokenService.verifyAnnotation.java)?.not() == true) {
            throw ExceptionUtils.internalException("尝试注入 USERID，但此Controller 未指定 TOKEN 注解")
        }
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