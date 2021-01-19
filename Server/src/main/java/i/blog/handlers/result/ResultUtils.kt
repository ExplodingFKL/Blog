package i.blog.handlers.result

import org.springframework.http.HttpStatus

object ResultUtils {
    fun ok(any: Any): Result = Result(HttpStatus.OK.value(), HttpStatus.OK.name, any)

    object Fail {
        fun nullPoint(msg: String = "程序内部错误") = Result(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null)
        fun unknown(message: String) = Result(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null)
        fun badRequest(msg: String) = Result(HttpStatus.BAD_REQUEST.value(), msg, null)
        fun notFound(path: String) = Result(HttpStatus.NOT_FOUND.value(), "path not found: $path ")
    }
}