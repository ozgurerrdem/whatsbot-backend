package com.whatsbot.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService uds;

    public JwtAuthFilter(JwtService jwtService, UserDetailsService uds) {
        this.jwtService = jwtService;
        this.uds = uds;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        String auth = req.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);
            try {
                var jws = jwtService.parse(token);
                String username = jws.getPayload().getSubject();
                String tenant = jws.getPayload().get("tenant", String.class);

                var ud = uds.loadUserByUsername(username);
                var authToken = new UsernamePasswordAuthenticationToken(
                        new PrincipalUser(username, tenant), null, ud.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } catch (JwtException ignored) {}
        }
        chain.doFilter(req, res);
    }
}
