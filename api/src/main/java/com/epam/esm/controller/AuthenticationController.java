package com.epam.esm.controller;

import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.dto.AuthenticationRequestDto;
import com.epam.esm.model.dto.AuthenticationResponseDto;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.entity.User;
import com.epam.esm.security.jwt.JwtProvider;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * The REST API Authentication controller.
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider tokenProvider;
    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JwtProvider tokenProvider,
                                    UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    /**
     * Signs in a user based on the provided credentials.
     *
     * @param authenticationRequestDto an authentication request object which contains email and password
     * @return a JWT token's data transfer object
     */
    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponseDto> signin(@Valid @RequestBody AuthenticationRequestDto authenticationRequestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequestDto.getEmail(),
                        authenticationRequestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new AuthenticationResponseDto(jwt));
    }

    /**
     * Signs up a user based on the provided information.
     *
     * @param userDto a user's data transfer object
     */
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public User signup(@Valid @RequestBody UserDto userDto) {
        try {
            return userService.create(userDto);
        } catch (ServiceException e) {
            throw new ResourceAlreadyExistException(e.getMessage());
        }
    }
}
