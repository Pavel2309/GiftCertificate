package com.epam.esm.security;

import com.epam.esm.model.entity.UserPrincipal;
import com.epam.esm.security.jwt.JwtProvider;
import com.epam.esm.service.impl.UserDetailsServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final String EMAIL = "email";
    private static final String NAME = "name";

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtProvider tokenProvider;

    public CustomAuthenticationSuccessHandler(UserDetailsServiceImpl userDetailsService, JwtProvider tokenProvider) {
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User auth2User = auth2AuthenticationToken.getPrincipal();
        String email = auth2User.getAttribute(EMAIL);
        String name = auth2User.getAttribute(NAME);
        UserPrincipal userPrincipal;
        userPrincipal = (UserPrincipal) userDetailsService.findOrSignUp(email, name);
        String jwt = tokenProvider.generateToken(userPrincipal);
        response.getWriter().write(jwt);
    }
}
