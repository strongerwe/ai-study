package cn.stronger.we.config;


import cn.stronger.we.redis.CustomRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author qiang.w
 * @version release-1.0.0
 * @class RedisConfiguration.class
 * @department Platform R&D
 * @date 2025/2/26
 * @desc do what?
 */
@Slf4j
@Configuration(value = "RedisConfiguration")
public class RedisConfiguration {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    @Bean
    public CustomRedisTemplate customRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        log.info(">>>>>>>> CustomRedisTemplate [Redis操作工具类] Bean Create Success!");
        return new CustomRedisTemplate(stringRedisTemplate);
    }
}
