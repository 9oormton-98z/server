package org.goormton.darktourism.security.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.goormton.darktourism.domain.member.Member;
import org.goormton.darktourism.security.auth.PrincipalDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    public final static int ACCESS_TOKEN_VALIDATION_SECOND = 1000 * 60 * 30; // 30분
    public final static int REFRESH_TOKEN_VALIDATION_SECOND = 1000 * 60 * 60 * 24 * 2; // 2일

//    @Value("${jwt.access-secret}")
    private final String secretKey;

//    @Value("${jwt.refresh-secret}")
    private final String refresh;

    public JwtUtil(
            @Value("${jwt.access-secret}") String secretKey,
            @Value("${jwt.refresh-secret}") String refresh) {
        this.secretKey = secretKey;
        this.refresh = refresh;
    }

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractAllClaims(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getPayload(String token) {
        return extractAllClaims(token).get("payload", String.class);
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    public String generateAccessToken(Member member) {
        
        return generateToken(member.getUsername(), secretKey, ACCESS_TOKEN_VALIDATION_SECOND);
    }

    public String generateRefreshToken(Member member) {
        return generateToken(member.getUsername(), refresh, REFRESH_TOKEN_VALIDATION_SECOND);
    }

    public String generateToken(String username, String secretKey, int expireTime) {
        log.info("generateJwtToken Start");
        
        Claims claims = Jwts.claims();
        claims.put("payload", username);

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(secretKey), SignatureAlgorithm.HS256)
                .compact();

        log.info("token : " + jwt);
        return jwt;
    }

    public boolean validateToken(String token, String secretKey) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey(secretKey)).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public Boolean validateToken(String token, PrincipalDetails principalDetails) {
        final String username = getPayload(token);

        return (username.equals(principalDetails.getUsername()) && !isTokenExpired(token));
    }

}
