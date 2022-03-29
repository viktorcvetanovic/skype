package com.viktor.skype.security;

import com.viktor.skype.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {
    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        if (userService.findByUsernameAndPassword(username, password) == null) {
            throw new AuthenticationCredentialsNotFoundException("Invalid login data");
        }
        return authentication;
    }
}
