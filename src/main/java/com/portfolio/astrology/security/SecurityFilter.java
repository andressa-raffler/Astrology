package com.portfolio.astrology.security;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class SecurityFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    public SecurityFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = tokenService.getTokenFromRequest(request);
        boolean validToken = tokenService.isTokenValid(token);
        if (validToken) {
            clientAuthentication(token);
        }
        filterChain.doFilter(request,response);
    }

    private void clientAuthentication(String token) {
        String email = tokenService.getEmailLogado(token);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

//    private String getTokenFromRequest(HttpServletRequest request){
//    String token = request.getHeader("Authorization");
//    if(token == null || !token.startsWith("Bearer ")) {
//        return null;
//    }
//    return token.substring(7);
//    }
}
