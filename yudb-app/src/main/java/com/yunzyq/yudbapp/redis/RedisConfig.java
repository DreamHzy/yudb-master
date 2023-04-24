package com.yunzyq.yudbapp.redis;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.time.Duration;


/**
 * @Description: TODO
 * @Author wuxubiao
 * @Date 2018/8/15 16:26
 * @Version V-1.0
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        return RedisCacheManager
                .builder(connectionFactory)
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(1)))
                .transactionAware()
                .build();
    }

//    @Bean
//    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.addMessageListener(new RedisExpiredListener(), new PatternTopic("__keyevent@0__:expired")); //__keyevent@0__:expired这表示 只接收0号库的key过期通知
//        return container;
//    }


    /**
     * RedisTemplate配置
     *
     * @param factory
     * @return
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        //定义value的序列化方式
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        /*ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);*/

//        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.setDefaultSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
