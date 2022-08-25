package com.sabancihan.registerservice.service;

import com.sabancihan.registerservice.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.Name;


@Service
@RequiredArgsConstructor
public class RegisterService {
    private final LdapTemplate ldapTemplate;
    private final PasswordEncoder passwordEncoder;

    private boolean userExists(Name usernameLookUp) {
        try {
            ldapTemplate.lookup(usernameLookUp);
            return true;
        } catch (org.springframework.ldap.NameNotFoundException e) {
            return false;
        }
    }


    public void register(RegisterRequest registerRequest) {

        var username = registerRequest.getUsername();


        var surname = registerRequest.getSurname();


        Name dn = LdapNameBuilder
                .newInstance()
                .add("ou", "people")
                .add("uid", username)
                .build();


        if (userExists(dn)) {
            throw new IllegalArgumentException("User already exists");
        }



        DirContextAdapter context = new DirContextAdapter(dn);

        context.setAttributeValues(
                "objectclass",
                new String[]{ "top", "person", "organizationalPerson", "inetOrgPerson" });

        context.setAttributeValue("uid", username);
        context.setAttributeValue("sn", surname);
        context.setAttributeValue("mail", registerRequest.getEmail());
        context.setAttributeValue("cn", registerRequest.getName() + " " + surname);
        context.setAttributeValue("userPassword", passwordEncoder.encode(registerRequest.getPassword()));

        ldapTemplate.bind(context);


    }
}
