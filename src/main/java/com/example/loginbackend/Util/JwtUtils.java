package com.example.loginbackend.Util;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private final static String secret = "this_is_secret";
    private final long milliTime = System.currentTimeMillis();
    private final Date issuedAt = new Date(milliTime);
    private final Date expiry = new Date(milliTime + (60 * 60 * 100));

    public String generateJwt(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(issuedAt)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            Date expiration = claims.getExpiration();
            Date current = new Date();
            return expiration == null || !current.after(expiration);
        } catch (Exception e) {
            return false;
        }
    }
    public String metaEmail (String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
