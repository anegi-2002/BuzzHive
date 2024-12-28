package com.socialMedia.BuzzHive.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JWTService {
    @Value("${jwt.secret.key}")
    private String secretKey;
    private static final String TOKEN = "token";
    private static final String EXPIRATION = "expiration";
    public JWTService(){
//        try {
//            KeyGenerator keyGen=KeyGenerator.getInstance("HmacSHA256");
//            SecretKey SK=keyGen.generateKey();
//            secretKey= Base64.getEncoder().encodeToString(SK.getEncoded());
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
    }

    public Map<String,Object> generateToken(String username, Authentication authentication) {
        Map<String,Object> claims=new HashMap<>();
        Map<String,Object> tokenInfo = new HashMap<>();
        claims.put("username",username);
        claims.put("roles",authentication.getAuthorities().stream().collect(Collectors.toList()));
        long currentTimeInMillisSecond = System.currentTimeMillis();
        long expirationTimeInMillisSecond = currentTimeInMillisSecond + 60*60*30*1000 ;
        String token = Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(currentTimeInMillisSecond))
                .expiration(new Date(expirationTimeInMillisSecond))
                .and()
                .signWith(getKey())
                .compact(); //30 minutes
        tokenInfo.put(TOKEN,token);
        tokenInfo.put(EXPIRATION,expirationTimeInMillisSecond);
        return tokenInfo;
    }

    private SecretKey getKey() {
        byte[] keybytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keybytes);

    }

    public String extractusername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T  extractClaim(String token, Function<Claims,T> claimResolver) {
        final Claims claims =extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final  String userName = extractusername(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }
}
