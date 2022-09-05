package com.sabancihan.apigateway.config;

import com.sabancihan.apigateway.ldap.LdapAuthenticationFailureHandler;
import com.sabancihan.apigateway.ldap.LdapAuthenticationSuccessHandler;
import com.sabancihan.apigateway.ldap.TokenAuthenticationManager;
import com.sabancihan.apigateway.ldap.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManagerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.LdapAuthenticator;
import org.springframework.security.ldap.authentication.PasswordComparisonAuthenticator;
import org.springframework.security.ldap.userdetails.InetOrgPersonContextMapper;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.util.matcher.*;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${environment.host}")
    private String environmentHost;

    private final LdapAuthenticationSuccessHandler successHandler;
    private final LdapAuthenticationFailureHandler failureHandler;

    private final TokenProvider tokenProvider;

    private final TokenAuthenticationManager authenticationManager;


    @Bean
    public ServerWebExchangeMatcher serverWebExchangeMatcher() {
        return exchange -> {
            Mono<ServerHttpRequest> request = Mono.just(exchange).map(ServerWebExchange::getRequest);
            return request.map(ServerHttpRequest::getHeaders)
                    .filter(h -> h.containsKey(HttpHeaders.AUTHORIZATION))
                    .flatMap($ -> ServerWebExchangeMatcher.MatchResult.match())
                    .switchIfEmpty(ServerWebExchangeMatcher.MatchResult.notMatch());
        };
    }

    @Bean
    public ServerAuthenticationConverter tokenAuthenticationConverter() {
        return exchange -> Mono.justOrEmpty(exchange)
                .map(tokenProvider::getTokenFromRequest)
                .filter(token -> !token.isEmpty())
                .map(tokenProvider::getAuthentication);
    }

    @Bean
    public AuthenticationWebFilter webFilter() {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(authenticationManager);
        authenticationWebFilter.setServerAuthenticationConverter(tokenAuthenticationConverter());
        authenticationWebFilter.setRequiresAuthenticationMatcher(serverWebExchangeMatcher());
        authenticationWebFilter.setSecurityContextRepository(NoOpServerSecurityContextRepository.getInstance());
        return authenticationWebFilter;
    }





    @Bean
    PasswordComparisonAuthenticator authenticator(BaseLdapPathContextSource contextSource) {
        PasswordComparisonAuthenticator authenticator = new PasswordComparisonAuthenticator(contextSource);
        authenticator.setUserDnPatterns(new String[] { "uid={0},ou=people" });




        authenticator.setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
        return authenticator;
    }




    @Bean
    @Primary
        LdapAuthenticationProvider reactiveAuthenticationProvider(LdapAuthenticator authenticator, LdapAuthoritiesPopulator populator) {
        LdapAuthenticationProvider provider = new LdapAuthenticationProvider(authenticator,populator);
        provider.setUserDetailsContextMapper(new InetOrgPersonContextMapper());
        return provider;

    }

    @Bean
    @Primary
    ReactiveAuthenticationManager reactiveAuthenticationManager(@Autowired LdapAuthenticationProvider provider) {

       AuthenticationManager manager =   new ProviderManager(provider);
        return new ReactiveAuthenticationManagerAdapter(manager);
    }






    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {

         serverHttpSecurity.
                 cors()
                 .and()
                 .csrf().csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse())
                 .requireCsrfProtectionMatcher(ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/login/**","/register/**"))
                 .and()
                 .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                 .authorizeExchange(exchange ->
                        exchange.pathMatchers("/eureka/**","/login/**","/register/**")
                                .permitAll()
                                .anyExchange()
                                .authenticated())
                .formLogin()
                 .authenticationSuccessHandler(successHandler)
                 .authenticationFailureHandler(failureHandler)

                 .and()
                .httpBasic()
                 .disable();

        serverHttpSecurity.addFilterBefore(webFilter(), SecurityWebFiltersOrder.FORM_LOGIN);

        return serverHttpSecurity.build();

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(String.format("http://%s:3000", environmentHost)));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT","PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization", "Accept", "XSRF-TOKEN","X-XSRF-TOKEN"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
