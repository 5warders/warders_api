package com.warders.api.common.component.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.warders.api.auth.service.CustomUserDetailsService;
import com.warders.api.user.domain.Role;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private static final long tokenValidTime = 30 * 60 * 1000L;

    private final CustomUserDetailsService customUserDetailsService;

    public String generateToken(String userPK, Role role) {
        Date now = new Date();
        return JWT.create()
            .withSubject(userPK)
            .withClaim("roles", role.toString())
            .withIssuedAt(now)
            .withExpiresAt(new Date(now.getTime() + tokenValidTime))
            .sign(Algorithm.HMAC256(secretKey));
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(this.getUserPK(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUserPK(String token) {
        return JWT.decode(token).getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }

    public boolean validateToken(String jwtToken) {
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
            jwtVerifier.verify(jwtToken);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

}