package com.sabancihan.apigateway.ldap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Component
@Getter
@Setter

public class RoleGatewayFilterFactory  extends AbstractGatewayFilterFactory<RoleGatewayFilterFactory.Config> {

    public RoleGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> ReactiveSecurityContextHolder.getContext().map(SecurityContext::getAuthentication)
                .flatMap(authentication -> {


                    if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(config.adminRole))) {
                        return chain.filter(exchange);
                    } else {
                        return Mono.error(new Exception("User has no admin role"));
                    }



                });
    }

    public static class Config {
        String adminRole = "ROLE_ADMİNS";
    }
}


