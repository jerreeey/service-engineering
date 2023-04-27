package com.example.demo.controller;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UsersRestController {

    @Autowired
    UsersService usersService;

    @PutMapping("/id/{userId}")
    public ResponseEntity loginUser(@PathVariable("userId") int userId, @RequestParam Optional<String> email, @RequestParam Optional<String> password) {
        try {
            return ResponseEntity.ok(usersService.updateUserByUserId(userId, email, password));
        } catch (UserNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

}
