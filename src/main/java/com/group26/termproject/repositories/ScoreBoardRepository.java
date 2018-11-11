package com.group26.termproject.repositories;

import com.group26.termproject.tables.ScoreBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreBoardRepository extends JpaRepository<ScoreBoard, Long> {
}
