package com.cars.RentACar.service;

import com.cars.RentACar.entity.User;
import com.cars.RentACar.exception.*;
import com.cars.RentACar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new HandlePSQLException("User with " + user.getUsername() + " already exists in database");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new HandlePSQLException("User with email" + user.getEmail() + " already exists in database");
        }
        userRepository.save(user);
    }

    public User login(User user, UUID userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with id: " + userId + " was not found in database");
        }

        User userDB = optionalUser.get();

        if (!userDB.getEmail().equals(user.getEmail())) {
            throw new UserNotAllowed("User with given email " + user.getEmail() + " can not access resource with id "
                    + userId);
        }
        boolean validPassword = passwordEncoder.matches(user.getPassword(), userDB.getPassword());
        if (!validPassword) {
            throw new PasswordNotFoundException("Wrong email or password");
        }

        if (!userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAndPasswordNotFoundException("Wrong email or password");
        }
        return userDB;
    }

    public void updateUser(User user, UUID uuid, String password) {

        Optional<User> optionalUser = userRepository.findById(uuid);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with id: " + uuid + " was not found in database");
        }
        User userDB = optionalUser.get();

        if (!userDB.getUsername().equals(user.getUsername())) {
            throw new UserNotAllowed("User with username " + user.getUsername() + " is not allowed to access resource with id: "
                    + uuid);
        }

        boolean validPassword = passwordEncoder.matches(user.getPassword(), userDB.getPassword());
        if (!validPassword) {
            throw new PasswordNotFoundException("Wrong email or password");
        }

        if (null != user.getUsername() && !"".equals(user.getUsername())) {
            userDB.setUsername(user.getUsername());
        }
        if (null != password && !"".equals(password)) {
            userDB.setPassword(passwordEncoder.encode(password));
        }
        if (null != user.getFirstName() && !"".equals(user.getFirstName())) {
            userDB.setFirstName(user.getFirstName());
        }
        if (null != user.getPhoneNumber() && !"".equals(user.getPhoneNumber())) {
            userDB.setPhoneNumber(user.getPhoneNumber());
        }
        if (null != user.getImage() && !"".equals(user.getImage())) {
            userDB.setImage(user.getImage());
        }
        if (null != user.getRole() && !"".equals(user.getRole())) {
            userDB.setRole(user.getRole());
        }
        userRepository.save(userDB);

    }

    public User getUserById(UUID uuid) {
        Optional<User> optionalUser = userRepository.findById(uuid);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with id: " + uuid + " was not found in database");
        }
        return optionalUser.get();
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void updateUser(User user, UUID userId,UUID adminId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with id: " + userId + " was not found in database");
        }
        User userDB = optionalUser.get();

        Optional<User> optionalAdmin = userRepository.findById(adminId);
        if (optionalAdmin.isEmpty()) {
            throw new UserNotFoundException("Admin with id: " + adminId + " was not found in database");
        }
        User adminDB = optionalAdmin.get();
        if(null == adminDB.getRole() || !adminDB.getRole().equals("ADMIN")){
            throw new UserNotAllowed("Provided id: " + adminId + " can not access this resource");
        }

        if (null != user.getUsername() && !"".equals(user.getUsername())) {
            userDB.setUsername(user.getUsername());
        }
        if (null != user.getEmail() && !"".equals(user.getEmail())) {
            userDB.setEmail(user.getEmail());
        }
        if (null != user.getFirstName() && !"".equals(user.getFirstName())) {
            userDB.setFirstName(user.getFirstName());
        }
        if (null != user.getLastName() && !"".equals(user.getLastName())) {
            userDB.setLastName(user.getLastName());
        }
        if (null != user.getPhoneNumber() && !"".equals(user.getPhoneNumber())) {
            userDB.setPhoneNumber(user.getPhoneNumber());
        }
        if (null != user.getPersonalNumber() && !"".equals(user.getPersonalNumber())) {
            userDB.setPersonalNumber(user.getPersonalNumber());
        }
        if (null != user.getImage() && !"".equals(user.getImage())) {
            userDB.setImage(user.getImage());
        }
        if (null != user.getRole() && !"".equals(user.getRole())) {
            userDB.setRole(user.getRole());
        }

        userRepository.save(userDB);
    }

    public void validateUser(UUID uuid){
        Optional<User> optionalUser = userRepository.findById(uuid);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with id: " + uuid + " was not found in database");
        }
        User userDB = optionalUser.get();
        if(!userDB.getRole().equals("ADMIN")){
            throw new UserNotAllowed("User with provided id: " + uuid + " is not allowed to visit this resource");
        }
    }


}
