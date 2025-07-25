package com.ms.Ecomm.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    //generates a secure random secret key for signing JWTs using the HMAC-SHA256 algorithm.
    // This key is regenerated every time your app restarts
    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // user identifier
                .setIssuedAt(new Date()) // time when token was created
                .setExpiration(new Date(System.currentTimeMillis() + 1000*3000)) // valid for 50 minutes
                .signWith(SECRET_KEY) // digitally signs the token
                .compact(); // generate the final JWT as a String
    }


    // This parses the token and returns the subject (email) that was set when generating the token.
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // using same secret key for decryption.
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // returns the email
    }


    // validating token here
    public boolean validateToken(String token, String email) {
        return extractEmail(token).equals(email) && !isTokenExpired(token);
    }

    // function for token expiration time
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build()
                .parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }
}
