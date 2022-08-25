package com.sabancihan.apigateway.config;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.crypto.SecretKey;


@Data
@EnableAsync
@Configuration
@ConfigurationProperties(prefix = "app")
public class TokenConfig {

    private String  tokenSecret;

    private long tokenExpirationMsec;

    public SecretKey getTokenSecret() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(tokenSecret));
    }


}