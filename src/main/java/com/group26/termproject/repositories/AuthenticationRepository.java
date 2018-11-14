package com.group26.termproject.repositories;

import com.group26.termproject.tables.Authentication;
import com.group26.termproject.tables.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthenticationRepository extends JpaRepository<Authentication,Integer> {

    @Query(value = "select * from Authentication a where a.token = token",nativeQuery = true)
    public Optional<Authentication> getByToken(String token);
}
