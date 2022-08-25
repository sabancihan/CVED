package com.sabancihan.apigateway.ldap;

import org.springframework.ldap.core.ContextSource;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.stereotype.Component;


@Component
public class CustomLdapAuthoritiesPopulator extends DefaultLdapAuthoritiesPopulator {

    public CustomLdapAuthoritiesPopulator(ContextSource contextSource) {
        super(contextSource, "ou=groups");
        this.setGroupSearchFilter("uniquemember={0}");
    }




}
