package com.ssafy.tripon.common.auth;

import static com.ssafy.tripon.common.exception.ErrorCode.ACCESS_TOKEN_EXPIRED;
import static com.ssafy.tripon.common.exception.ErrorCode.UNAUTHORIZED;

import com.ssafy.tripon.common.exception.CustomException;
import com.ssafy.tripon.member.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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

    public JwtTokenProvider(@Value("${jwt.secretKey}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public Token createAccessToken(Member member) {
        Date now = new Date();
        Date accessTokenExpirationTime = new Date(now.getTime() + accessTokenExpirationMillis);

        String jti = UUID.randomUUID().toString();

        return new Token(Jwts.builder()
                .setId(jti)
                .setSubject(member.getEmail())
                .claim("role", member.getRole())
                .setExpiration(accessTokenExpirationTime)
                .signWith(secretKey)
                .compact());
    }

    public Token createRefreshToken(Member member) {
        Date now = new Date();
        Date refreshTokenExpirationTime = new Date(now.getTime() + refreshTokenExpirationMillis);

        String jti = UUID.randomUUID().toString();

        return new Token(Jwts.builder()
                .setId(jti)
                .setSubject(member.getEmail())
                .setExpiration(refreshTokenExpirationTime)
                .signWith(secretKey)
                .compact());
    }

    public String getJti(Token token) {
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token.token());
        return claims.getBody().getId();
    }

    public Date getExpiration(Token token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token.token())
                .getBody().getExpiration();
    }

    public String getMemberEmail(Token token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token.token())
                .getBody()
                .getSubject();
    }

    public void validateToken(Token token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token.token());
        } catch (ExpiredJwtException e) {
            throw new CustomException(ACCESS_TOKEN_EXPIRED);
        } catch (JwtException | IllegalArgumentException e) {
            throw new CustomException(UNAUTHORIZED);
        }
    }
}
