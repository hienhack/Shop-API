package com.example.tutorial.util;

import com.example.tutorial.entity.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret_key}")
    private String JWT_SECRET;

    @Value("${jwt.expiration}")
    private long JWT_EXPIRATION;

    public String generateToken(User user) {
        Date now  = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(Integer.toString(user.getId()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }

    public Integer getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
        return Integer.parseInt(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Date now = new Date();
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
//        } catch (MalformedJwtException ex) {
//            System.err.println("Invalid JWT token");
//        } catch (ExpiredJwtException ex) {
//            System.err.println("Expired JWT token");
//        } catch (UnsupportedJwtException ex) {
//            System.err.println("Unsupported JWT token");
//        } catch (IllegalArgumentException ex) {
//            System.err.println("JWT claims string is empty.");
//        } catch (SignatureException ex) {
//            System.out.println("Caught Incorrect signature");
        } catch (JwtException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
