package com.example.demo.service;

import com.example.demo.DTO.UsersDTO;
import com.example.demo.entity.Users;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public UsersDTO save(UsersDTO usersDTO) {
        Users user = new Users();
        user.setEmail(usersDTO.getEmail());
        user.setPassword(usersDTO.getPassword());
        user.setBookings(new HashSet<>());
        user = usersRepository.save(user);
        return convertUserToUserDTO(usersRepository.findUserByUserId(user.getUserId()));
    }

    private UsersDTO convertUserToUserDTO(Users user) {
        UsersDTO usersDTO = new UsersDTO();
        usersDTO.setUserId(user.getUserId());
        usersDTO.setEmail(user.getEmail());
        return usersDTO;
    }

    public UsersDTO updateUserByUserId(int userId, Optional<String> email, Optional<String> password) throws UserNotFoundException {
        Users user = usersRepository.findUserByUserId(userId);
        if (user != null) {
            email.ifPresent(user::setEmail);
            password.ifPresent(s -> user.setPassword(passwordEncoder.encode(s)));
            Users savedUser = usersRepository.save(user);
            return convertUserToUserDTO(savedUser);
        }
        throw new UserNotFoundException("User with Id " + userId + " not found");
    }
}
