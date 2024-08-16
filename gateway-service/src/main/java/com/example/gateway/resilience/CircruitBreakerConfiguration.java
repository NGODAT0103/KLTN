package com.example.gateway.resilience;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CircruitBreakerConfiguration {

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCircruitBreaker(){
    return f -> f.configureDefault(id ->
    {
        CircuitBreakerConfig circuitBreakerConfig = new CircuitBreakerConfig.Builder()
                .slidingWindowSize(10)
                .permittedNumberOfCallsInHalfOpenState(2)
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(5))
                .build();
        return new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(circuitBreakerConfig)
                .build();
    });




    }
}
