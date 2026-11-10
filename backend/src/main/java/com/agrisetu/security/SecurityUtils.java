package com.agrisetu.security;

import com.agrisetu.entity.User;
import com.agrisetu.exception.UnauthorizedException;
import com.agrisetu.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityUtils {

    private final UserRepository userRepository;

    public SecurityUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getCurrentUserOptional() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return Optional.empty();
        }
        return userRepository.findByEmailIgnoreCase(authentication.getName().trim());
    }

    public User getCurrentUser() {
        return getCurrentUserOptional()
                .orElseThrow(() -> new UnauthorizedException("User not authenticated"));
    }
}
