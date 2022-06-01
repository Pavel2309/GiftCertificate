package com.epam.esm.service.impl;

import com.epam.esm.converter.impl.UserPrincipalMapper;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserPrincipalMapper userPrincipalMapper;

    @Autowired
    UserDetailsServiceImpl(UserRepository userRepository, UserPrincipalMapper userPrincipalMapper) {
        this.userRepository = userRepository;
        this.userPrincipalMapper = userPrincipalMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return userPrincipalMapper.userToPrincipal(user);
    }
}
