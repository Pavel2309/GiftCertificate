package com.epam.esm.model.security;

import com.epam.esm.model.entity.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserPrincipal userToPrincipal(User user) {
        UserPrincipal userp = new UserPrincipal();
        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getTitle())).collect(Collectors.toList());

        userp.setUsername(user.getEmail());
        userp.setPassword(user.getPassword());
        userp.setEnabled(user.isEnabled());
        userp.setAuthorities(authorities);
        return userp;
    }
}
