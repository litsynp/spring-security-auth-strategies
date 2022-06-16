package com.litsynp.springsec.jwt.global.auth;

import com.litsynp.springsec.jwt.global.util.JwtUtil;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    /**
     * Authenticate user using JWT token from request and chain the filter.
     *
     * @param request HTTP request
     * @param response HTTP Response
     * @param filterChain Filter chain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            String bearerToken = getBearerTokenFromAuthorizationHeader(request);

            // If JWT Bearer token is present
            if (bearerToken != null && jwtUtil.validateToken(bearerToken)) {
                String email = jwtUtil.getEmailFromToken(bearerToken);

                // Authenticate user with email parsed from JWT
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                // Create authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                // Set authentication
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e.toString());
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Take Bearer token from 'Authorization' header from request.
     *
     * @param request HTTP request
     * @return Bearer Token without "Bearer " if present else <code>null</code>
     */
    private String getBearerTokenFromAuthorizationHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        // Take JWT token after "Bearer "
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        // If JWT token is not present
        return null;
    }
}
