package com.sabancihan.apigateway.ldap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LdapSummary {
    private String user_id;
    private String email;
}
