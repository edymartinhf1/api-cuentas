package com.bootcamp.bank.cuentas.config.redis;

import com.bootcamp.bank.cuentas.model.dao.CuentaDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisCuentaConfig {

    @Bean
    @Primary
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
        return new LettuceConnectionFactory("localhost", 6379);
    }

    @Bean(name="redisCuentaOperations")
    public ReactiveRedisOperations<String, CuentaDao> redisCuentaOperations(ReactiveRedisConnectionFactory factory) {
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<CuentaDao> valueSerializer = new Jackson2JsonRedisSerializer<>(CuentaDao.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, CuentaDao> builder =
                RedisSerializationContext.newSerializationContext(keySerializer);

        RedisSerializationContext<String, CuentaDao> context =
                builder.value(valueSerializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }

    @Bean
    public ReactiveValueOperations reactiveValueOperations(ReactiveRedisTemplate reactiveRedisTemplate) {
        return reactiveRedisTemplate.opsForValue();
    }

    @Bean
    @Primary
    public ReactiveRedisTemplate<String, CuentaDao> reactiveRedisTemplate(
            ReactiveRedisConnectionFactory factory) {
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<CuentaDao> valueSerializer =
                new Jackson2JsonRedisSerializer<>(CuentaDao.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, CuentaDao> builder =
                RedisSerializationContext.newSerializationContext(keySerializer);
        RedisSerializationContext<String, CuentaDao> context =
                builder.value(valueSerializer).build();
        return new ReactiveRedisTemplate<>(factory, context);
    }


}
