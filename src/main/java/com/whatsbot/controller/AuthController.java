package com.whatsbot.controller;

import com.whatsbot.domain.Role;
import com.whatsbot.domain.User;
import com.whatsbot.repository.UserRepository;
import com.whatsbot.security.JwtService;
import com.whatsbot.security.PrincipalUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserRepository userRepo;

    public AuthController(AuthenticationManager authManager, JwtService jwtService, UserRepository userRepo) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.userRepo = userRepo;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest body,
                                               @RequestHeader("X-Tenant-ID") String tenantKey) {
        // Kimlik doğrulama
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(body.username(), body.password())
        );

        // Kullanıcı ve tenant kontrolü
        User user = userRepo.findByUsername(body.username()).orElseThrow();
        if (!tenantKey.equals(user.getTenantKey())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // JWT üret
        String token = jwtService.generate((UserDetails) auth.getPrincipal(), tenantKey);

        // UserSummaryDto hazırla
        UserSummaryDto userDto = toSummary(user);

        // token + user birlikte dön
        return ResponseEntity.ok(new LoginResponse(token, userDto));
    }

    @GetMapping("/me")
    public ResponseEntity<UserSummaryDto> me(@AuthenticationPrincipal PrincipalUser pu) {
        // Principal'dan username ile DB'den tam kullanıcıyı çekip DTO'ya çevir
        User user = userRepo.findByUsername(pu.username()).orElseThrow();
        return ResponseEntity.ok(toSummary(user));
    }

    // --- Helpers & DTOs ---

    private static UserSummaryDto toSummary(User u) {
        Set<String> roleNames = u.getRoles()
                .stream()
                .map(Role::getName) // Role entity'nde alan adı farklıysa uyarlayın (örn: getCode())
                .collect(Collectors.toSet());

        return new UserSummaryDto(
                u.getId(),
                u.getUsername(),
                u.getTenantKey(),
                u.getFirstName(),
                u.getLastName(),
                roleNames
        );
    }

    public record LoginRequest(String username, String password) {}

    public record LoginResponse(String token, UserSummaryDto user) {}

    public record UserSummaryDto(
            Long id,
            String username,
            String tenantKey,
            String firstName,
            String lastName,
            Set<String> roles
    ) {}
}
