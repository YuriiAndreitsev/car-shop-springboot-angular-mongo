package com.bezkoder.springjwt.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.request.LoginRequest;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.bezkoder.springjwt.payload.response.JwtResponse;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.security.jwt.JwtUtils;
import com.bezkoder.springjwt.services.UserDetailsImpl;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        log.warn("LoginRequest : username: {} ; password : {}", loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        }catch (AuthenticationException ex){
            log.error("UNABLE TO AUTHENTICATE IN SIGNIN CONTROLLER :\n {}", ex.getMessage());
        }
        log.warn("BEFORE SET AUTHENTICATION");
        if (authentication!=null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.warn("AFTER SET AUTHENTICATION");
            String jwt = jwtUtils.generateJwtToken(authentication);
            log.info("JWT TOKEN FOR LOGIN USER : {}", jwt);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            log.info("userDetailsImpl Principal : {}", userDetails.getUsername());
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
        } else  {
            return ResponseEntity.ok(new ResponseEntity<Object>(HttpStatus.OK));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

//        Set<String> strRoles = signUpRequest.getRole();
        user.setRoles(signUpRequest.getRole());
        userRepository.insert(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
