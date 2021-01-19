package i.blog.handlers.exceptions

import org.springframework.http.HttpStatus

object ExceptionUtils {
    fun httpRequestException(httpStatus: HttpStatus): ApplicationException =
        ApplicationException(httpStatus.value(), httpStatus.reasonPhrase)

    fun badRequest(msg: String = "Bad Request!"): ApplicationException {
        return ApplicationException(400, msg)
    }

    fun internalException(msg: String = "未知错误！"): ApplicationException = ApplicationException(500, msg)

    fun forbidden(contextPath: String): ApplicationException {
        return ApplicationException(403, "没有权限 $contextPath")
    }

    fun notFound(path: String): ApplicationException {
        return ApplicationException(404, "路径 $path 未找到.")
    }

    /**
     *  错误信息处理
     *
     * @property errorId Int
     * @property errorMessage String
     * @constructor
     */
    class ApplicationException(val errorId: Int, val errorMessage: String = "未知错误！") : RuntimeException(errorMessage) {
    }
}