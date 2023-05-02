package com.example.demo.repository;

import com.example.demo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    @Query(value = "SELECT * FROM Users u WHERE u.user_id =?1",
            nativeQuery = true)
    Users findUserByUserId(@Param("userId") int userId);

    Optional<Users> findByEmail(@Param("email") String email);
}
