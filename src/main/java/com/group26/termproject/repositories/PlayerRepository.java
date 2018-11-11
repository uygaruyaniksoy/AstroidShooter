package com.group26.termproject.repositories;

import com.group26.termproject.tables.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player,Integer> {
//    public Player getByToken(String token);
    public Optional<Player> findByEmailAndPassword(String email, String password);
}
