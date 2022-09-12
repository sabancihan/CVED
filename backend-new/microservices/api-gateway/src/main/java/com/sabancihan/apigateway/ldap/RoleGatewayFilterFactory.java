package com.sabancihan.apigateway.ldap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class RoleGatewayFilterFactory  extends AbstractGatewayFilterFactory<RoleGatewayFilterFactory.Config> {

    public RoleGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> ReactiveSecurityContextHolder.getContext().map(SecurityContext::getAuthentication)
                .flatMap(authentication -> {

                    authentication.getAuthorities().forEach( it ->
                            log.info("Role: {}", it.getAuthority()));



                    if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(config.adminRoleTurkish) || a.getAuthority().equals(config.adminRoleEnglish))) {
                        return chain.filter(exchange);
                    } else {
                        return Mono.error(new Exception("User has no admin role"));
                    }



                });
    }

    public static class Config {
        String adminRoleTurkish = "ROLE_ADMİNS";
        String adminRoleEnglish = "ROLE_ADMINS";
    }
}


