package com.cars.RentACar.controller;

import com.cars.RentACar.dto.AdminRequestDTO;
import com.cars.RentACar.entity.User;
import com.cars.RentACar.mapper.UserMapper;
import com.cars.RentACar.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {

    private final UserMapper userMapper;
    private final UserService userService;

    @PatchMapping("/admin/update/{id}")
    public ResponseEntity<Void> updateUser(@RequestBody AdminRequestDTO adminRequestDTO,
                                           @PathVariable UUID id,
                                           @RequestHeader("id") UUID uuid) {
        User user = userMapper.mapToEntity(adminRequestDTO);
        userService.updateUser(user, id,uuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
