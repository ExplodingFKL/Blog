package i.blog.handlers.errorAdvice

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.openEdgn.logger4k.getLogger
import i.blog.handlers.exceptions.ExceptionUtils
import i.blog.handlers.result.Result
import i.blog.handlers.result.ResultUtils
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.DispatcherServlet
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
import javax.annotation.PostConstruct
import javax.annotation.Resource


@Configuration
@RestControllerAdvice
class ResponseAdvice : ResponseBodyAdvice<Any> {
    @Resource
    private lateinit var dispatcherServlet: DispatcherServlet


    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean {
        return returnType.parameterType != String::class.java
    }

    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): Any? {
        val requestPath = request.uri.path
        val result = if (body == null) {
            ResultUtils.Fail.nullPoint()
        } else {
            if (requestPath.contains(Regex("swagger"))) {
                formatData(body)
            } else {
                if (body is Result) {
                    formatData(body)
                } else {
                    ResultUtils.ok(formatData(body))
                }
            }
        }
        if (result is Result) {
            response.setStatusCode(HttpStatus.valueOf(result.code))
        }
        return result
    }

    private fun formatData(body: Any): Any {
        return if (body.javaClass == String::class.java) {
            val objectMapper = ObjectMapper()
            objectMapper.writeValueAsString(body)
            return objectMapper
        } else {
            body
        }
    }

    private val logger = getLogger()

    @ResponseBody
    @ExceptionHandler(value = [Exception::class])
    @ResponseStatus
    fun exceptionHandler(e: Exception): Result {
        logger.errorThrowable("发送服务器内部错误", e)
        return ResultUtils.Fail.unknown("服务器内部错误")
    }

    @ResponseBody
    @ExceptionHandler(value = [ExceptionUtils.ApplicationException::class])
    fun formatCheckExceptionHandler(e: ExceptionUtils.ApplicationException): Result {
        logger.warn("触发错误：{} ", e.message)
        return Result(e.errorId, e.errorMessage)
    }

    @ResponseBody
    @ExceptionHandler(value = [HttpMessageNotReadableException::class])
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    fun postFormatError(e: Exception): Result {
        logger.errorThrowable("内容不合法", e)
        return ResultUtils.Fail.badRequest("内容不合法！")
    }

    @ResponseBody
    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    fun formatCheckExceptionHandler(e: MethodArgumentNotValidException): Result {
        logger.warn("客户端请求错误：{} ", e.message)
        val builder = StringBuilder()
        e.bindingResult.allErrors.forEach {
            val param = if (it is FieldError) {
                it.field
            } else {
                ""
            }
            builder.append("字段 $param ").append(it.defaultMessage ?: "格式化异常")
        }
        return ResultUtils.Fail.badRequest(builder.toString())
    }


}