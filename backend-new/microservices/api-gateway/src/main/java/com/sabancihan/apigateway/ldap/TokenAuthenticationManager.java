package com.sabancihan.apigateway.ldap;

import lombok.RequiredArgsConstructor;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.naming.Name;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@RequiredArgsConstructor
@Component
public class TokenAuthenticationManager implements ReactiveAuthenticationManager {
    private final LdapTemplate ldapTemplate;

    private final TokenProvider tokenProvider;
    private final LdapAuthoritiesPopulator authoritiesPopulator;


    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

         var uid = authentication.getName() + "w";

        try {
            var context = ldapTemplate.searchForContext(query().where("uid").is(uid));

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(authentication.getName(), null,  authoritiesPopulator.getGrantedAuthorities(context,uid));
            return Mono.just(auth);
        }
        catch (Exception e) {


            Name dn = LdapNameBuilder
                    .newInstance()
                    .add("ou", "people")
                    .add("uid", uid)
                    .build();
            DirContextAdapter newContext = new DirContextAdapter(dn);

            newContext.setAttributeValues(
                    "objectclass",
                    new String[]
                            { "top",
                                    "person",
                                    "organizationalPerson",
                                    "inetOrgPerson" });
            newContext.setAttributeValue("cn", uid);
            newContext.setAttributeValue("sn", uid);
            newContext.setAttributeValue
                    ("userPassword", uid);

            ldapTemplate.bind(newContext);


            return Mono.empty();
        }
    }
}
