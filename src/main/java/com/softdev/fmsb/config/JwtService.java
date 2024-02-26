package com.softdev.fmsb.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "DaMWAruSgec1/aUlk2z46uNw/8yUt4g8dP76+1NgYDzgcJmy0DPrx7y/5t+UqVlsiEQpj4MGHv4tllEr8o3jT9trI9nqLAGSlye2Qd6ZnF/27h7gko1/0/G89BSXGukvXl2DzJubZzq9CG06OQQC9Bcn5UDe2PIZBXk1uL9o55JLvR5UtARxHbZMZC8qOfxpI0VFc7a2n4LYeIT7ba0sPU3fW2eKMnwgHgfi7dY8GLTwECdluJ2f2D67FFOuTkovaLwXVs0BBvaaBsED7VOzu036wUd/bQifuCadDir8CNBrWe95H34naRSJYCkqQRiZLqe3f1JCAGOZEeeZN1KFsz5//WtMRpl+oXAWFjL6ImI=";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generatedToken(new HashMap<>(), userDetails);
    }

    public String generatedToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 *24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
