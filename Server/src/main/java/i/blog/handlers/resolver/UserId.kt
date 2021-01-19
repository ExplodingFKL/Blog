package i.blog.handlers.resolver

import io.swagger.v3.oas.annotations.Parameter

/**
 * 在controller 层注入 user Id
 */
@Parameter(hidden = true)
annotation class UserId