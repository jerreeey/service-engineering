package com.example.demo.repository;

import com.example.demo.entity.Tokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Tokens, Integer> {

  @Query(value = " select t from Tokens t inner join Users u on t.user.userId = u.userId where u.userId = :id and (t.expired = false or t.revoked = false)")
  List<Tokens> findAllValidTokenByUser(@Param("id") Integer id);

  Optional<Tokens> findByToken(@Param("tokens") String token);
}
