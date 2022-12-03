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

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Collections;
import java.util.Date;

@Getter
@Setter
public class TokenUseful {
    private static final String HEADER = "Authorization";
    private static final String PREFIX = "Bearer ";
    private static final Long EXPIRATION = 12*60*60*1000L;
    private static final String SECRET_KEY = "a291418b695c4604968ce6546fa9d8c8";
    private static final String ISSUER = "Raffler";

    public static String createToken(User user){
        Key securityKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        String token = Jwts.builder()
                           .setSubject(user.getEmail())
                           .setIssuer(ISSUER)
                           .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                           .signWith(securityKey, SignatureAlgorithm.HS256)
                            .compact();

        return PREFIX + token;
    }

    private static boolean validExpiration(Date expiration) {
        return expiration.after(new Date(System.currentTimeMillis()));
    }

    private static boolean validIssuer(String issuer) {
        return issuer.equals(ISSUER);
    }

    private static boolean validSubject(String username) {
        return username != null && username.length() > 0;
    }

    public static UsernamePasswordAuthenticationToken validate(HttpServletRequest request) {
        String token = request.getHeader(HEADER);
        token = token.replace(PREFIX, "");
        Jws<Claims> jwsClaims = Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes())
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

}
