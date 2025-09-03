package com.whatsbot.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secret; // Base64 encoded secret bekliyoruz

    @Value("${app.jwt.expiresInSeconds:36000}")
    private long expiresInSeconds;

    private SecretKey getSigningKey() {
        // Base64 decode -> 256+ bit key garanti
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generate(UserDetails ud, String tenantKey) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(ud.getUsername())
                .claim("tenant", tenantKey)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(expiresInSeconds)))
                .signWith(getSigningKey()) // Algoritma anahtardan otomatik seçilir (HS256/384/512)
                .compact();
    }

    public Jws<Claims> parse(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey()) // aynı key ile doğrula
                .build()
                .parseSignedClaims(token);
    }
}
