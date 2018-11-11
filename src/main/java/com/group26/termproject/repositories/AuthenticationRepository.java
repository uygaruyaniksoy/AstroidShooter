package com.group26.termproject.repositories;

import com.group26.termproject.tables.Authentication;
import com.group26.termproject.tables.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationRepository extends JpaRepository<Authentication,Integer> {
//    public Player getByToken(String token);
}
