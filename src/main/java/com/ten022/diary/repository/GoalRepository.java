package com.ten022.diary.repository;

import com.ten022.diary.domain.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Long> {

    @Query(value = "select g from Goal g where year(g.date) = :year and month(g.date) = :month and g.member.id = :member")
    Optional<Goal> findIdByYearAndMonthAndMember(@Param("year") int year, @Param("month") int month, @Param("member") Long memberId);
}
