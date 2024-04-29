package de.akhmadjonov.project.FirstSecurityApp.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import de.akhmadjonov.project.FirstSecurityApp.security.JWTUtil;
import de.akhmadjonov.project.FirstSecurityApp.services.PersonDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final PersonDetailsService personDetailsService;
    private final JWTUtil jwtUtil;

    @Autowired
    public JWTFilter(PersonDetailsService personDetailsService, JWTUtil jwtUtil) {
        this.personDetailsService = personDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeaders = request.getHeader("Authorization");

        if(authHeaders != null && !authHeaders.isBlank() && authHeaders.startsWith("Bearer ")) {
            String jwt = authHeaders.substring(7);

            if (jwt.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
            } else {
                try {
                    String name = jwtUtil.validateTokenAndRetrieveData(jwt);

                    UserDetails userDetails = personDetailsService.loadUserByUsername(name);

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

                    if(SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                } catch (JWTVerificationException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
