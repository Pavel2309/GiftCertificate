package com.epam.esm.service.impl;

import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.PagedModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyMap;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private static UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl service;

    User user;
    List<User> users;
    PagedModel<User> pagedModel;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("User");

        users = new ArrayList<>();
        users.add(user);

        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(10, 1, 2, 1);
        pagedModel = PagedModel.of(users, metadata);
    }

    @AfterEach
    void tearDown() {
        user = null;
        users = null;
    }

    @Test
    void findAll() {
        Mockito.when(userRepository.findAll(anyMap())).thenReturn(pagedModel);
        PagedModel<User> actual = service.findAll(anyMap());
        Assertions.assertEquals(pagedModel, actual);
    }

    @Test
    void findOne() {
        Mockito.when(userRepository.findOne(anyLong())).thenReturn(Optional.ofNullable(user));
        Optional<User> actual = service.findOne(anyLong());
        Assertions.assertEquals(user, actual.get());
    }
}