package i.blog.handlers.errorAdvice

import i.blog.handlers.exceptions.ExceptionUtils
import i.blog.handlers.result.ResultUtils
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.boot.autoconfigure.web.ServerProperties
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes
import org.springframework.http.HttpStatus
import javax.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import javax.servlet.http.HttpServletResponse
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("\${server.error.path:\${error.path:/error}}")
class ErrorController(serverProperties: ServerProperties) :
    BasicErrorController(DefaultErrorAttributes(), serverProperties.error) {


    /**
     * 覆盖默认的JSON响应
     */
    override fun error(request: HttpServletRequest): ResponseEntity<Map<String, Any>> {
        val status = getStatus(request)
        if (status == HttpStatus.NO_CONTENT) {
            return ResponseEntity(status)
        }
        val body = getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.ALL))
        val path = (body.get("path") ?: "").toString()
        throw ExceptionUtils.notFound(path)
    }


    /**
     * 覆盖默认的HTML响应
     */
    override fun errorHtml(request: HttpServletRequest, response: HttpServletResponse): ModelAndView {
        //请求的状态
        val status = getStatus(request)
        response.status = getStatus(request).value()
        val model = getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.TEXT_HTML))
        val modelAndView = resolveErrorView(request, response, status, model)
        //指定自定义的视图
        return modelAndView ?: ModelAndView("error", model)
    }
}