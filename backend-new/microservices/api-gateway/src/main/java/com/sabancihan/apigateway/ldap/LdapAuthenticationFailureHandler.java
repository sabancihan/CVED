package com.sabancihan.apigateway.ldap;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;



@Component
public class LdapAuthenticationFailureHandler implements ServerAuthenticationFailureHandler {

    private final ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();



    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {




            webFilterExchange.getExchange().getResponse().setStatusCode(HttpStatus.FORBIDDEN);

        return Mono.empty();

    }
}
