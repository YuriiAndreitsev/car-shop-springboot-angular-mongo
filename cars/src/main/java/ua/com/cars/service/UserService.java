package ua.com.cars.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
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
        User user = userRepository.findByUsername(username).orElseThrow(()-> new IllegalArgumentException("User "+username+" not found"));

        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role.getName());
    }

    public User getUser(String username) {
        log.info("Fetching user {} from DB", username);
        return userRepository.findByUsername(username).orElse(null);
    }


    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }
}