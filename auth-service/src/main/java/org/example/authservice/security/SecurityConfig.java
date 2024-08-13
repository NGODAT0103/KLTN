package org.example.authservice.security;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.CookieRequestCache;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {


    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    SecurityFilterChain oauth2FilterChain(HttpSecurity http) throws Exception {

        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults());
      OAuth2AuthorizationServerConfigurer oAuth2AuthorizationServerConfigurer =  http.getConfigurer(OAuth2AuthorizationServerConfigurer.class);
      oAuth2AuthorizationServerConfigurer.tokenRevocationEndpoint( e ->
              e.revocationResponseHandler( (request, response, authentication) -> {
                          SecurityContext securityContext = SecurityContextHolder.getContext();
                          if(securityContext != null){
                              SecurityContextHolder.clearContext();
                          }
                  request.getSession().invalidate();
                  response.setStatus(200);
                  response.getWriter().write("Token revoked");
              }
              )
      );



        http.headers( h-> h.addHeaderWriter(new TraceHeaderWriter()));
        http.rememberMe(AbstractHttpConfigurer::disable);
        return http.build();
    }
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.logout(AbstractHttpConfigurer::disable);


        http.headers( h-> h.addHeaderWriter(new TraceHeaderWriter()));


       http.oauth2ResourceServer(resource ->
               resource.jwt(jwt -> jwt.
                       jwkSetUri("http://localhost:8001/api/v1/auth/oauth2/jwsk")
                       .decoder(jwtDecoder())
                               .jwtAuthenticationConverter(jwtAuthenticationConverter()
               )
               ));

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/v1/auth/account/userinfo").hasAuthority("auth-service.read")
                .requestMatchers( HttpMethod.GET,"/api/v1/auth/actuator/prometheus").permitAll()
                .anyRequest().permitAll()
        );
       return http.build();
    }

    @Bean
    AuthorizationServerSettings authorizationServerSettings(){
        return AuthorizationServerSettings.builder().build();
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



    JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withJwkSetUri("http://localhost:8001/api/v1/auth/oauth2/jwks").build();
    }
    JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;

    }

}