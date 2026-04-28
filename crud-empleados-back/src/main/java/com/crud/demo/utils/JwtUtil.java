package com.crud.demo.utils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.crud.demo.entity.Role;
import com.crud.demo.entity.User;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

  Dotenv dotenv = Dotenv.load();
  private final String SECRET = dotenv.get("JWT_SECRET");

  SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

  public String generateToken(User user) {
    List<String> roles = user.getRoles()
        .stream()
        .map(Role::getName)
        .toList();

    return Jwts.builder()
        .subject(String.valueOf(user.getId()))
        .claim("roles", roles)
        .claim("email", user.getEmail())
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + 86400000))
        .signWith(key)
        .compact();
  }

  public UsernamePasswordAuthenticationToken validateToken(String token) {
    try {
      Claims claims = Jwts.parser()
          .verifyWith(key)
          .build()
          .parseSignedClaims(token)
          .getPayload();

      Long userId = claims.get("userId", Long.class);

      List<String> roles = claims.get("roles", List.class);

      List<SimpleGrantedAuthority> authorities = roles.stream()
          .map(SimpleGrantedAuthority::new)
          .toList();

      return new UsernamePasswordAuthenticationToken(userId, null, authorities);

    } catch (Exception e) {
      throw new RuntimeException("Invalid token");
    }
  }
}
