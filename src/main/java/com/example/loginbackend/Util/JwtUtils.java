package com.example.loginbackend.Util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private static String secret="this_is_secret";
    private long milliTime=System.currentTimeMillis();
    private Date issuedAt=new Date(milliTime);
    private Date expiry =new Date(milliTime+(60*60*100));

    public String generateJwt(String email){
        Claims claims= Jwts.claims().
                setIssuer(email)
                .setIssuedAt(issuedAt)
                .setExpiration(expiry);
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }
    public void validateToken(String token) throws Exception {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        }catch (Exception e){
            throw new Exception("Invalid token");
        }
    }
    public String metaEmail(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getIssuer();
    }
}
