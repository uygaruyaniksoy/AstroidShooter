package com.group6.termproject.repositories;

import com.group6.termproject.tables.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthenticationRepository extends JpaRepository<Authentication,Integer> {

    @Query(value = "select * from Authentication a where a.token = token",nativeQuery = true)
    Optional<Authentication> getByToken(String token);
}
