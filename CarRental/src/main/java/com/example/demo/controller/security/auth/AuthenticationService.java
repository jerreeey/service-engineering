package com.example.demo.controller.security.auth;

import com.example.demo.DTO.UsersDTO;
import com.example.demo.controller.security.auth.enumeration.Role;
import com.example.demo.controller.security.auth.enumeration.TokenType;
import com.example.demo.controller.security.config.JwtService;
import com.example.demo.entity.Tokens;
import com.example.demo.entity.Users;
import com.example.demo.repository.TokenRepository;
import com.example.demo.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  @Autowired
  UsersRepository usersRepository;
  @Autowired
  TokenRepository tokenRepository;
  @Autowired
  PasswordEncoder passwordEncoder;
  @Autowired
  JwtService jwtService;
  @Autowired
  AuthenticationManager authenticationManager;

  public AuthenticationResponse register(AuthenticationRequest request) {
    Users user = new Users();
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setRole(Role.ROLE_USER);
    Users savedUser = usersRepository.save(user);
    String jwtToken = jwtService.generateToken(user);
    saveUserToken(savedUser, jwtToken);
    AuthenticationResponse authenticationResponse = new AuthenticationResponse();
    authenticationResponse.setAccessToken(jwtToken);
    authenticationResponse.setUserDTO(convertUserToUserDTO(savedUser));
    return authenticationResponse;
  }

  private UsersDTO convertUserToUserDTO(Users user) {
    UsersDTO usersDTO = new UsersDTO();
    usersDTO.setUserId(user.getUserId());
    usersDTO.setEmail(user.getEmail());
    return usersDTO;
  }


  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    Authentication authentication = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
    authenticationManager.authenticate(authentication);
    Users user = usersRepository.findByEmail(request.getEmail()).orElseThrow();
    String jwtToken = jwtService.generateToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    AuthenticationResponse authenticationResponse = new AuthenticationResponse();
    authenticationResponse.setAccessToken(jwtToken);
    authenticationResponse.setUserDTO(convertUserToUserDTO(user));
    return authenticationResponse;
  }

  private void saveUserToken(Users user, String jwtToken) {
    Tokens token = new Tokens();
    token.setUser(user);
    token.setToken(jwtToken);
    token.setTokenType(TokenType.BEARER);
    token.setExpired(false);
    token.setRevoked(false);
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(Users user) {
    List<Tokens> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUserId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(tokens -> {
      tokens.setExpired(true);
      tokens.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

}
