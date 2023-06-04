package com.portfolio.astrology.security;

import com.portfolio.astrology.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.security.Key;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@Getter
@Setter
@Service
public class TokenService {
    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";
    private Long expiration = 12*60*60*1000L;
    private final String SECRET_KEY = "Up3bdcmamXeDez7zNW9Uz9Fy7UW4j8Q9";
    private final String ISSUER = "Raffler";

    public String createToken(User user){
        Key securityKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        String token = Jwts.builder()
                           .setSubject(user.getEmail())
                           .setIssuer(ISSUER)
                           .setExpiration(new Date(System.currentTimeMillis() + expiration))
                           .signWith(securityKey, SignatureAlgorithm.HS256)
                           .compact();

        return PREFIX + token;
    }
    private  boolean validExpiration(Date expiration) {
        return expiration.after(new Date(System.currentTimeMillis()));
    }

    private boolean validIssuer(String issuer) {
        return issuer.equals(ISSUER);
    }

    private boolean validSubject(String username) {
        return username != null && username.length() > 0;
    }

    public UsernamePasswordAuthenticationToken validate(HttpServletRequest request) {
        String token = request.getHeader(HEADER);
        token = token.replace(PREFIX, "");
        Jws<Claims> jwsClaims = Jwts.parserBuilder()
                                    .setSigningKey(SECRET_KEY.getBytes())
                                    .build()
                                    .parseClaimsJws(token);

        String userEmail = jwsClaims.getBody().getSubject();
        String issuer = jwsClaims.getBody().getIssuer();
        Date expiration = jwsClaims.getBody().getExpiration();

        if (validSubject(userEmail) && validIssuer(issuer) && validExpiration(expiration)) {
            return new UsernamePasswordAuthenticationToken(userEmail, null, Collections.emptyList());
            //null é o perfil do usuario e Collections é a lista de endpoints que pode acessar
        }

        return null;
    }

    public boolean isTokenValid(String tokenEntry) {
        try {

           String token = tokenEntry.replace(PREFIX, "");
           Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .build()
                    .parseClaimsJws(token);
           return true;
        }catch (Exception e){
            return false;
        }
    }


    public String getEmailLogado(String token){
        token = token.replace(PREFIX, "");
        Jws<Claims> jwsClaims = Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token);
        String userEmail = jwsClaims.getBody().getSubject();
        return userEmail;
    }


    public String getTokenFromRequest(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if(token == null || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.substring(7);
    }






}
