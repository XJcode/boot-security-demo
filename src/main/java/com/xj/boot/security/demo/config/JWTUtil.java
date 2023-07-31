package com.xj.boot.security.demo.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.Key;

public class JWTUtil {

    private static final String secret = "tJALd4uRw4oXSo4RQn3GONU4bTbAnljD";

    public static String create() {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder()
                .setSubject("xxx")
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public static String decode(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    // Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws).getBody().getSubject()
}
