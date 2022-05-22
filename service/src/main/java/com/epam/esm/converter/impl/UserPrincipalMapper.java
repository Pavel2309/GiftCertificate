package com.epam.esm.converter.impl;

import com.epam.esm.model.entity.User;
import com.epam.esm.model.entity.UserPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserPrincipalMapper {

    private final String ROLE_PREFIX = "ROLE_";

    public UserPrincipal userToPrincipal(User user) {
        UserPrincipal userPrincipal = new UserPrincipal();
        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role.getTitle())).collect(Collectors.toList());
        userPrincipal.setUsername(user.getEmail());
        userPrincipal.setPassword(user.getPassword());
        userPrincipal.setEnabled(user.isEnabled());
        userPrincipal.setAuthorities(authorities);
        return userPrincipal;
    }
}
