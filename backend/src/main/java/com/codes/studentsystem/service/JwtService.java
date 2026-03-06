package com.codes.studentsystem.service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
   private final Key key;
   private final long expMillis;

   public JwtService(@Value("${app.jwt.secret}") String secret,
                     @Value("${app.jwt.expSeconds}") long expSeconds){
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expMillis = expSeconds * 1000;
       }

    public String generateToken(Integer userId, String username, String role){
        Date now = new Date();
        Date exp = new Date(now.getTime() + expMillis);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(exp)
                .addClaims(Map.of("role", role, "userId", userId))
                .signWith(key)
                .compact();
    }

    public Integer parseUserId(String token) {
        Object v = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId");

        if (v == null) return null;
        if (v instanceof Integer i) return i;
        if (v instanceof Number n) return n.intValue();
        return Integer.parseInt(v.toString());
    }

    public String parseUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String parseRole(String token) {
        Object role = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .get("role");
        return role == null ? null : role.toString();
    }

}
