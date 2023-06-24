package com.cars.RentACar.mapper;

import com.cars.RentACar.dto.*;
import com.cars.RentACar.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    public User mapToEntity(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return user;
    }

    public UserSuccessfulRegisterDTO mapToUserSuccessfulRegisterDTO(User user) {
        UserSuccessfulRegisterDTO userResponseDTO = new UserSuccessfulRegisterDTO();
        userResponseDTO.setSuccessful(true);
        userResponseDTO.setMessage(user.getUsername() + " - " + user.getEmail() + " je uspesno registrovan");
        return userResponseDTO;
    }

    public User mapToEntity(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());
        return user;
    }
    public UserSuccessfulLoginDTO mapToUserSuccessfulLoginDTO(User user) {
        UserSuccessfulLoginDTO userSuccessfulLogin = new UserSuccessfulLoginDTO();
        userSuccessfulLogin.setSuccessful(true);
        userSuccessfulLogin.setUserId(user.getUserId());
        return userSuccessfulLogin;
    }

    public User mapToEntity(UserRequestInfoDTO userRequestInfoDTO){
        User user = new User();
        user.setUsername(userRequestInfoDTO.getUsername());
        user.setPassword(userRequestInfoDTO.getPassword());
        user.setFirstName(userRequestInfoDTO.getFirstName());
        user.setLastName(userRequestInfoDTO.getLastName());
        user.setPhoneNumber(userRequestInfoDTO.getPhoneNumber());
        user.setImage(userRequestInfoDTO.getImage());
        user.setRole(userRequestInfoDTO.getRole());
        return user;
    }

    public UserInfoResponseDTO mapToUserInfoResponseDTO(User user){
        UserInfoResponseDTO userInfoResponseDTO = new UserInfoResponseDTO();
        userInfoResponseDTO.setUsername(user.getUsername());
        userInfoResponseDTO.setEmail(user.getEmail());
        userInfoResponseDTO.setFirstName(user.getFirstName());
        userInfoResponseDTO.setLastName(user.getLastName());
        userInfoResponseDTO.setPhoneNumber(user.getPhoneNumber());
        userInfoResponseDTO.setPersonalNumber(user.getPersonalNumber());
        userInfoResponseDTO.setImage(user.getImage());
        return userInfoResponseDTO;
    }

    public List<UserInfoResponseDTO> mapToUserInfoResponseDTO(List<User> users){
        return users.stream().map(this::mapToUserInfoResponseDTO).collect(Collectors.toList());
    }

    public User mapToEntity(AdminRequestDTO adminRequestDTO){
        User user = new User();
        user.setUsername(adminRequestDTO.getUsername());
        user.setEmail(adminRequestDTO.getEmail());
        user.setFirstName(adminRequestDTO.getFirstName());
        user.setLastName(adminRequestDTO.getLastName());
        user.setPhoneNumber(adminRequestDTO.getPhoneNumber());
        user.setPersonalNumber(adminRequestDTO.getPersonalNumber());
        user.setImage(adminRequestDTO.getImage());
        user.setRole(adminRequestDTO.getRole());
        return user;
    }

}
