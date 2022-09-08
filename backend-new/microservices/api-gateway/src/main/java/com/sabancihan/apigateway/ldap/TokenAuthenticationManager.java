package com.sabancihan.apigateway.ldap;

import lombok.RequiredArgsConstructor;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.ldap.userdetails.InetOrgPerson;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.naming.Name;

import java.util.Map;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@RequiredArgsConstructor
@Component
public class TokenAuthenticationManager implements ReactiveAuthenticationManager {
    private final LdapTemplate ldapTemplate;

    private final TokenProvider tokenProvider;
    private final LdapAuthoritiesPopulator authoritiesPopulator;


    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

         var uid = authentication.getName();



            var context = ldapTemplate.searchForContext(query().where("uid").is(uid));

            //Get email from LDAP
            var email = context.getStringAttribute("mail");


            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(LdapSummary.builder().email(email).user_id(uid).build(), null,  authoritiesPopulator.getGrantedAuthorities(context,uid));
            return Mono.just(auth);


    }
}
