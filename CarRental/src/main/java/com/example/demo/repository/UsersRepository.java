package com.example.demo.repository;

import com.example.demo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    @Query(value = "SELECT * FROM Users u WHERE u.user_id =?1",
            nativeQuery = true)
    Users findUserByUserId(int userId);

    Optional<Users> findByEmail(String email);
}
