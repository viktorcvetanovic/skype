package com.viktor.skype.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.viktor.skype.data.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class JwtToken {
    private static final Algorithm algorithm = Algorithm.HMAC256("secret");


    public static String generateAccessToken(User user) {
        //hard coded

        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withClaim("roles",
                        user.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .sign(algorithm);
    }

    public static String generateRefreshToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .sign(algorithm);
    }

    public static Authentication readToken(HttpServletRequest request) {
        String token = request.getHeader("access_token");
        System.out.println(token);
        if(token==null){
            throw new RuntimeException();
        }
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return createAuthentication(decodedJWT);
    }

    private static UsernamePasswordAuthenticationToken createAuthentication(DecodedJWT decodedJWT) {
        String username = decodedJWT.getSubject();
        String[] roles = decodedJWT.getClaim("roles")
                .asArray(String.class);
        Collection<SimpleGrantedAuthority> authorities = stream(roles).map(e -> new SimpleGrantedAuthority(e))
                .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(username, authorities);
    }
}
