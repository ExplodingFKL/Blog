package i.blog.handlers.result

data class Result(
    val code:Int,
    val message:String,
    val data:Any? = null
)
