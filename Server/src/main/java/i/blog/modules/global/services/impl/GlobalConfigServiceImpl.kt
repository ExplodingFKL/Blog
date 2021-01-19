package i.blog.modules.global.services.impl

import i.blog.modules.global.models.entities.GlobalConfigEntity
import i.blog.modules.global.repository.GlobalConfigRepository
import i.blog.modules.global.services.IGlobalConfigService
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import javax.annotation.Resource

/**
 * 带redis 缓存的配置存储
 *
 */
@Service
class GlobalConfigServiceImpl : IGlobalConfigService {
    private lateinit var redisTemplate: RedisTemplate<String, String>

    @Resource
    private lateinit var globalConfigRepository: GlobalConfigRepository

    private val rKey = "blog_config"

    override fun getConfig(key: String, defaultValue: String): String {
        val opsForHash = redisTemplate.opsForHash<String, String>()
        val cacheData = opsForHash.get(rKey, key)
        if (cacheData != null) {
            return cacheData
        }
        val sqlData = globalConfigRepository.findById(key)
        return if (sqlData.isEmpty) {
            defaultValue
        } else {
            val v = sqlData.get().v
            opsForHash.put(rKey, key, v)
            v
        }
    }

    override fun setConfig(key: String, value: String): String {
        globalConfigRepository.save(GlobalConfigEntity(key, value))
        redisTemplate.opsForHash<String, String>().put(rKey, key, value)
        return value
    }
}