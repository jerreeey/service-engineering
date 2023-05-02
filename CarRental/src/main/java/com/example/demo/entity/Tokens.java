package com.example.demo.entity;

import com.example.demo.controller.security.auth.enumeration.TokenType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tokens {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_generator")
  @SequenceGenerator(name = "token_generator", sequenceName = "token_seq", allocationSize = 1)
  public Integer id;

  @Column(unique = true)
  public String token;

  @Enumerated(EnumType.STRING)
  public TokenType tokenType = TokenType.BEARER;

  public boolean revoked;

  public boolean expired;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @JsonIgnore
  public Users user;
}
