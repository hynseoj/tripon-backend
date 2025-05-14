package com.ssafy.tripon.common.auth;

import com.ssafy.tripon.member.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;

    @Value("${jwt.accessToken.expirationMillis}")
    private long accessTokenExpirationMillis;

    @Value("${jwt.refreshToken.expirationMillis}")
    private long refreshTokenExpirationMillis;

    public JwtTokenProvider(@Value("${secretKey}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public JwtToken createAccessToken(Member member) {
        Date now = new Date();
        Date accessTokenExpirationTime = new Date(now.getTime() + accessTokenExpirationMillis);

        String jti = UUID.randomUUID().toString();

        return new JwtToken(Jwts.builder()
                .setId(jti)
                .setSubject(member.getEmail())
                .claim("role", member.getRole())
                .setExpiration(accessTokenExpirationTime)
                .signWith(secretKey)
                .compact());
    }

    public String getJti(JwtToken token) {
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token.token());
        return claims.getBody().getId();
    }

    public String getMemberEmail(JwtToken token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token.token())
                .getBody()
                .getSubject();
    }

    public boolean validateToken(JwtToken token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token.token());
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
