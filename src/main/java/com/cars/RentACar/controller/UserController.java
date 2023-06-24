package com.cars.RentACar.controller;

import com.cars.RentACar.dto.*;
import com.cars.RentACar.entity.User;
import com.cars.RentACar.mapper.UserMapper;
import com.cars.RentACar.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping("/users/register")
    public ResponseEntity<UserSuccessfulRegisterDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        User user = userMapper.mapToEntity(userDTO);
        userService.createUser(user);
        UserSuccessfulRegisterDTO userResponseDTO = userMapper.mapToUserSuccessfulRegisterDTO(user);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }


    @PostMapping("/users/login/{userId}")
    public ResponseEntity<UserSuccessfulLoginDTO> login(@RequestBody UserRequestDTO userRequestDTO,@PathVariable UUID userId) {
        User user = userMapper.mapToEntity(userRequestDTO);
        User userDB = userService.login(user,userId);
        UserSuccessfulLoginDTO userSuccessfulLogin = userMapper.mapToUserSuccessfulLoginDTO(userDB);
        return ResponseEntity.ok(userSuccessfulLogin);

    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<Void> updateUser(@Valid @RequestBody UserRequestInfoDTO userRequestInfoDTO, @PathVariable UUID id) {
        User user = userMapper.mapToEntity(userRequestInfoDTO);
        userService.updateUser(user, id, userRequestInfoDTO.getNewPassword());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserInfoResponseDTO> getUser(@PathVariable UUID id) {
        User user = userService.getUserById(id);
        UserInfoResponseDTO userInfoResponseDTO = userMapper.mapToUserInfoResponseDTO(user);
        return ResponseEntity.ok(userInfoResponseDTO);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserInfoResponseDTO>> getAllUsers(@RequestHeader("id") UUID userId) {
        userService.validateUser(userId);
        List<User> users = userService.getUsers();
        List<UserInfoResponseDTO> userInfoResponseDTOS = userMapper.mapToUserInfoResponseDTO(users);
        return ResponseEntity.ok(userInfoResponseDTOS);
    }


}
