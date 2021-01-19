package i.blog.handlers

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * 处理前端 Long 精度丢失的问题
 */
@Configuration
class ObjectFormatConfig {
    @Bean("jackson2ObjectMapperBuilderCustomizer")
    fun jackson2ObjectMapperBuilderCustomizer(): Jackson2ObjectMapperBuilderCustomizer {
        return Jackson2ObjectMapperBuilderCustomizer { jacksonObjectMapperBuilder ->
            jacksonObjectMapperBuilder.serializerByType(Long::class.javaObjectType, ToStringSerializer.instance)
                .serializerByType(Long::class.java, ToStringSerializer.instance)
        }
    }
}