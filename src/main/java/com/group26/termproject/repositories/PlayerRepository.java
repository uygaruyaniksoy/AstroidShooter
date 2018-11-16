package com.group26.termproject.repositories;

import com.group26.termproject.tables.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player,Integer> {
//    public Player getByToken(String token);
    Optional<Player> findByEmailAndPassword(String email, String password);
    Optional<Player> findByEmail(String email);
    @Query(value = "select * from player p, authentication a where a.token = ?1 and a.pid = p.id", nativeQuery = true)
    Optional<Player> findByToken(String token);

}
