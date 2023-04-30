package com.example.demo.controller;

import com.example.demo.controller.security.auth.UserUpdateRequest;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersRestController {

    @Autowired
    UsersService usersService;

    @PutMapping("/{userId}")
    public ResponseEntity updateUser(@PathVariable("userId") int userId, @RequestBody UserUpdateRequest request) {
        try {
            return ResponseEntity.ok(usersService.updateUserByUserId(userId, request.getEmail(), request.getPassword()));
        } catch (UserNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable("userId") int userId) {
        try {
            usersService.deleteUserByUserId(userId);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

}
