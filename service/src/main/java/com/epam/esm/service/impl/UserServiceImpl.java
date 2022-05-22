package com.epam.esm.service.impl;

import com.epam.esm.converter.impl.UserMapper;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.UserRepository;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public PagedModel<User> findAll(Map<String, String> pageParameters) {
        return userRepository.findAll(pageParameters);
    }

    @Override
    public Optional<User> findOne(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User create(UserDto userDto) throws ServiceException {
        Optional<User> existedUser = userRepository.findByEmail(userDto.getEmail());
        if (existedUser.isPresent()) {
            throw new ServiceException("email " + userDto.getEmail());
        }
        User user = userMapper.convertDtoToEntity(userDto);
        return userRepository.create(user);
    }
}
