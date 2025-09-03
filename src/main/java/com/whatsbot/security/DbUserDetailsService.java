package com.whatsbot.security;

import com.whatsbot.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class DbUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    public DbUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var u = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("not found"));
        var auths = u.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPasswordHash(), auths);
    }
}
