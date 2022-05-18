package com.epam.esm.service.impl;

import com.epam.esm.converter.impl.UserMapper;
import com.epam.esm.converter.impl.UserPrincipalMapper;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserPrincipalMapper userPrincipalMapper;
    private final UserMapper userMapper;

    @Autowired
    UserDetailsServiceImpl(UserRepository userRepository, UserPrincipalMapper userPrincipalMapper, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userPrincipalMapper = userPrincipalMapper;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return userPrincipalMapper.userToPrincipal(user);
    }

    public UserDetails findOrSignUp(String email, String name) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            UserDto userDto = new UserDto();
            userDto.setName(name);
            userDto.setEmail(email);
            User newUser = userMapper.convertDtoToEntity(userDto);
            User createdUser = userRepository.create(newUser);
            return userPrincipalMapper.userToPrincipal(createdUser);
        }
        return userPrincipalMapper.userToPrincipal(user.get());
    }
}
