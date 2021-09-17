package com.bezkoder.springjwt.security.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bezkoder.springjwt.services.UserDetailsServiceImpl;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    public static final String BEARER = "Bearer ";
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        if (httpServletRequest.getServletPath().equals("/api/login") || httpServletRequest.getServletPath().equals("/api/token/refresh")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            String authHeader = httpServletRequest.getHeader(AUTHORIZATION);
            if (authHeader != null && authHeader.startsWith(BEARER)) {
                try {
                    String token = authHeader.substring(BEARER.length());
                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                } catch (Exception e) {
                    log.error("Error loggin in : {}", e.getMessage());
                    httpServletResponse.setHeader("error", e.getMessage());
                    httpServletResponse.setStatus(FORBIDDEN.value());
                    Map<String, String> error = new HashMap<>();
                    error.put("error_message", e.getMessage());
                    httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(httpServletResponse.getOutputStream(), error);
                }
            } else {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }
        }
//		try {
//			String jwt = parseJwt(request);
//			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
//				String username = jwtUtils.getUserNameFromJwtToken(jwt);
//
//				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//						userDetails, null, userDetails.getAuthorities());
//				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//				SecurityContextHolder.getContext().setAuthentication(authentication);
//			}
//		} catch (Exception e) {
//			logger.error("Cannot set user authentication: {}", e);
//		}
//
//		filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }
}
