package com.sabancihan.apigateway.ldap;

import com.sabancihan.apigateway.config.TokenConfig;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenProvider {

    private final TokenConfig tokenConfig;

    public String createToken(Authentication authentication) {




        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + tokenConfig.getTokenExpirationMsec());



        return Jwts.builder()
                .setSubject((authentication.getName()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(tokenConfig.getTokenSecret())
                .compact();
    }

    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(tokenConfig.getTokenSecret())
                .build()
                .parseClaimsJws(token)
                .getBody();


        return claims.getSubject();
    }

    public String getTokenFromRequest(ServerWebExchange serviceWebExchange) {

        var request = serviceWebExchange.getRequest();
        var headers = request.getHeaders();
        var authorization = headers.getFirst("Authorization");
        return authorization != null && authorization.startsWith("Bearer ") ?  authorization.substring(7) : "";
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(tokenConfig.getTokenSecret())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String userId = claims.getSubject();
        return userId != null ? new UsernamePasswordAuthenticationToken(userId, null, null) : null;
    }

    public boolean validateToken(String authToken) {
        try {

            Jwts.parserBuilder()
                    .setSigningKey(tokenConfig.getTokenSecret())
                    .build()
                    .parseClaimsJws(authToken);

            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

}