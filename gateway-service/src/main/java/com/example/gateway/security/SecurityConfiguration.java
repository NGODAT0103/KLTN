package com.example.gateway.security;
import com.example.gateway.security.filter.AddJwtHeaderFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;

@Configuration
public class SecurityConfiguration {


    @Bean
    SecurityWebFilterChain httpSecurity(ServerHttpSecurity http, ReactiveOAuth2AuthorizedClientService reactiveOAuth2AuthorizedClientService){
        http.cors(ServerHttpSecurity.CorsSpec::disable);
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        http.authorizeExchange(exchange ->exchange
                        .pathMatchers("/favicon.ico").permitAll()
                        .pathMatchers("/api/v1/auth/oauth2/authorize").permitAll()
                        .pathMatchers("/api/v1/auth/login").permitAll()
                        .pathMatchers("/api/v1/auth/oauth2/token").permitAll()
                        .pathMatchers("/login/oauth2/code/gateway-service").permitAll()
                        .pathMatchers("/api/v1/user/get-me").authenticated()
                        .pathMatchers("/ui-docs/").permitAll()
                        .pathMatchers("/actuator/**").authenticated()
                        .anyExchange().permitAll()

        );

        http.oauth2Client(Customizer.withDefaults());
        http.oauth2Login(Customizer.withDefaults());

        http.addFilterAfter(new AddJwtHeaderFilter(reactiveOAuth2AuthorizedClientService), SecurityWebFiltersOrder.AUTHORIZATION);
        WebSessionServerSecurityContextRepository webSessionServerSecurityContextRepository = new WebSessionServerSecurityContextRepository();
        webSessionServerSecurityContextRepository.setCacheSecurityContext(false);
        http.securityContextRepository(webSessionServerSecurityContextRepository);
        return http.build();
    }






















}
