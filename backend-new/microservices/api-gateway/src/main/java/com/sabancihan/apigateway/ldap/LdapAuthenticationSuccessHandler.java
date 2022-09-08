package com.sabancihan.apigateway.ldap;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;



@Component
@RequiredArgsConstructor

public class LdapAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {
    private final ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();
    private final TokenProvider tokenProvider;


    private final ObjectMapper objectMapper;
    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {

        String token = tokenProvider.createToken(authentication);

       ServerHttpResponse response =   webFilterExchange.getExchange().getResponse();

       response.setStatusCode(HttpStatus.OK);
       response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json");


        response.getHeaders().set(HttpHeaders.AUTHORIZATION, "Bearer " + token);




        return Mono.empty();

    }

}
