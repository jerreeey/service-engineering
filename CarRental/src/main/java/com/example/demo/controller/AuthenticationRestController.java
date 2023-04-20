package com.example.demo.controller;

import com.example.demo.controller.security.auth.AuthenticationRequest;
import com.example.demo.controller.security.auth.AuthenticationResponse;
import com.example.demo.controller.security.auth.AuthenticationService;
import com.example.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
public class AuthenticationRestController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/sessions")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
