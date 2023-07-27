package com.bootcamp.bank.cuentas.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
@Configuration
public class CuentasCircuitBreakerConfig {

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer(){
        return factory -> factory.configureDefault(id -> {
            return new Resilience4JConfigBuilder(id)
                    .circuitBreakerConfig(CircuitBreakerConfig.custom()
                            .slidingWindowSize(10)
                            .failureRateThreshold(50)
                            .waitDurationInOpenState(Duration.ofSeconds(10L))
                            .permittedNumberOfCallsInHalfOpenState(5)
                            .slowCallRateThreshold(50)
                            .slowCallDurationThreshold(Duration.ofSeconds(2L))
                            .build())
                    .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(3)).build())
                    .build();
        });
    }


    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomize() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build()).build());
    }


    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> findByIdCliCBCusomtize(){
        return factory->{
            factory.configure(builder->builder
                            .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(5)).build())
                            .circuitBreakerConfig(io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.ofDefaults())
                    ,"findByIdCliCB");
        };
    }

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> findByIdBCusomtize(){
        return factory->{
            factory.configure(builder->builder
                            .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(3)).build())
                            .circuitBreakerConfig(io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.ofDefaults())
                    ,"findByIdCB");
        };
    }

}
