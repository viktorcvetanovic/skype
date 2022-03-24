package com.viktor.skype.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.viktor.skype.data.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    protected User user;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        readRequest(request);
        System.out.println("caoo");
        System.out.println(user);
        return super.attemptAuthentication(request, response);
    }


    private void readRequest(HttpServletRequest request) {
        if (user != null) {
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            user = mapper.readValue(request.getInputStream(), User.class);
        } catch (IOException e) {
            e.printStackTrace();
            user = null;
        }
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        readRequest(request);
        return user.getPassword();
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        readRequest(request);
        return user.getUsername();
    }
}
