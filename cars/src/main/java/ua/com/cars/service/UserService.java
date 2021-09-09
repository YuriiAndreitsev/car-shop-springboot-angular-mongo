package ua.com.cars.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.com.cars.model.Role;
import ua.com.cars.model.User;
import ua.com.cars.repository.RoleRepository;
import ua.com.cars.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    public static final String BEARER = "Bearer ";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            log.error("{} not found in DB", username);
            throw new UsernameNotFoundException("User not found in DB");
        } else {
            log.info("Got {} user from DB", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }


    public User saveUser(User user) {
        log.info("saving new user {} to the DB", user.getName());
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.insert(user);
    }

    public Role saveRole(Role role) {
        log.info("saving new role {} to the DB", role.getName());
        return roleRepository.save(role);
    }


    public void addRoleToUser(String username, String roleName) {
        log.info("adding role {} to user {}", roleName, username);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User " + username + " not found"));
        user.getRoles().add(roleName);
    }

    public User getUser(String username) {
        log.info("Fetching user {} from DB", username);
        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User " + username + " not found"));
    }


    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    public void refreshToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String authHeader) throws IOException {
            try {
                String refresh_token = authHeader.substring(BEARER.length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = getUser(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(httpServletRequest.getRequestURL().toString())
                        .withClaim("roles", new ArrayList<>(user.getRoles()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(httpServletResponse.getOutputStream(), tokens);
            } catch (Exception e) {
                log.error("Error loggin in : {}", e.getMessage());
                httpServletResponse.setHeader("error", e.getMessage());
                httpServletResponse.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(httpServletResponse.getOutputStream(), error);
            }
        }

}