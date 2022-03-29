package com.viktor.skype.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.viktor.skype.data.entity.User;
import com.viktor.skype.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final UserService userService;

    protected AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService) {
        setAuthenticationManager(authenticationManager);
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        return getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        String username = (String) authResult.getPrincipal();
        User user= (User) userService.loadUserByUsername(username);
        String refreshToken = JwtToken.generateRefreshToken(user);
        String accessToken = JwtToken.generateAccessToken(user);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader("access_token", accessToken);
        response.setHeader("refresh_token", refreshToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        System.out.println("CAOOOOOOOO");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
    }
}
