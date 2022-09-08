package com.sabancihan.apigateway.ldap;


import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Optional;

@Component
@Order(-1)

public class AuthFilter implements GlobalFilter {




    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        return ReactiveSecurityContextHolder.getContext().map(SecurityContext::getAuthentication)
                .flatMap(authentication -> {


                    LdapSummary ldapSummary = (LdapSummary) authentication.getPrincipal();


                    exchange.getRequest().mutate().header("user_id", ldapSummary.getUser_id());

                    exchange.getRequest().mutate().header("user_email", ldapSummary.getEmail());






                    //Add user roles to headers
                    var role = authentication.getAuthorities().stream().findFirst();

                    if (role.isEmpty()) {
                        return Mono.error(new Exception("User has no role"));
                    }


                    exchange.getRequest().mutate().header("user_role", role.get().getAuthority());
                    return chain.filter(exchange);

                });
    }
}
