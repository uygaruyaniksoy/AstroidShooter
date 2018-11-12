package com.group26.termproject.repositories;

import com.group26.termproject.tables.ScoreBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Tuple;
import java.util.List;

public interface ScoreBoardRepository extends JpaRepository<ScoreBoard, Long> {

    @Query("select s.player.nickName, max(score) from ScoreBoard s where  s.date=CURRENT_DATE group by s.player.nickName")
    List<Tuple> getDailyLeaderboard();



    @Query("select s.player.nickName, max(score) from ScoreBoard s " +
            "where WEEK(s.date)=WEEK(CURRENT_DATE) and YEAR(CURRENT_DATE)=YEAR(s.date) " +
            "group by s.player.nickName")
    List<Tuple> getWeeklyLeaderboard();
}
