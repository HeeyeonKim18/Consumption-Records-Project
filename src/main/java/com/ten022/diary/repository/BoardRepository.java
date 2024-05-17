package com.ten022.diary.repository;

import com.ten022.diary.domain.Board;
import com.ten022.diary.dto.report.ReportResponseInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    /**
     * 월별 통계
     * @param year
     * @param month
     * @param memberId
     * @return
     */

    // 구매 카테고리 조회(월별)
    @Query(value = "select categories as keyword, count(id) as value " +
            "from board " +
            "where year(date) = :year and month(date) = :month and member_id = :memberId " +
            "group by categories order by value desc", nativeQuery = true)
    List<ReportResponseInterface> findCategories(@Param("year") int year, @Param("month") int month,
                                                 @Param("memberId") Long memberId);

    // 구매 감정 조회(월별)
    @Query(value = "select emotions as keyword, count(id) as value " +
            "from board " +
            "where year(date) = :year and month(date) = :month and member_id = :memberId " +
            "group by emotions order by value desc", nativeQuery = true)
    List<ReportResponseInterface> findEmotions(@Param("year") int year, @Param("month") int month,
                                                 @Param("memberId") Long memberId);
    // 구매 요인 조회(상위 5개, 월별)
    @Query(value = "select factors as keyword, count(id) as value " +
            "from board " +
            "where year(date) = :year and month(date) = :month and member_id = :memberId " +
            "group by factors order by value desc limit 5", nativeQuery = true)
    List<ReportResponseInterface> findFactors(@Param("year") int year, @Param("month") int month,
                                                 @Param("memberId") Long memberId);

    // 구매 만족도 조회(상위 5개, 월별)
    @Query(value = "select satisfaction as keyword, count(id) as value " +
            "from board " +
            "where year(date) = :year and month(date) = :month and member_id = :memberId " +
            "group by satisfaction order by value desc limit 5", nativeQuery = true)
    List<ReportResponseInterface> findSatisfactions(@Param("year") int year, @Param("month") int month,
                                                 @Param("memberId") Long memberId);

    // 구매 평균 만족도 조회(월별)
    @Query(value = "select avg(b.satisfaction) " +
            "from Board b " +
            "where year(b.date) = :year and month(b.date) = :month and b.member.id = :memberId ")
    Long findAvg(@Param("year") int year, @Param("month") int month,
                                                 @Param("memberId") Long memberId);

    /**
     * 연별 통계
     * @param year
     * @param memberId
     * @return
     */
    // 구매 카테고리 조회(연별)
    @Query(value = "select categories as keyword, count(id) as value " +
            "from board " +
            "where year(date) = :year and member_id = :memberId " +
            "group by categories order by value desc", nativeQuery = true)
    List<ReportResponseInterface> findCategories(@Param("year") int year, @Param("memberId") Long memberId);

    // 구매 감정 조회(연별)
    @Query(value = "select emotions as keyword, count(id) as value " +
            "from board " +
            "where year(date) = :year and member_id = :memberId " +
            "group by emotions order by value desc", nativeQuery = true)
    List<ReportResponseInterface> findEmotions(@Param("year") int year, @Param("memberId") Long memberId);

    // 구매 요인 조회(상위 5개, 연별)
    @Query(value = "select factors as keyword, count(id) as value " +
            "from board " +
            "where year(date) = :year and member_id = :memberId " +
            "group by factors order by value desc limit 5", nativeQuery = true)
    List<ReportResponseInterface> findFactors(@Param("year") int year, @Param("memberId") Long memberId);

    // 구매 만족도 조회(상위 5개, 연별)
    @Query(value = "select satisfaction as keyword, count(id) as value " +
            "from board " +
            "where year(date) = :year and member_id = :memberId " +
            "group by satisfaction order by value desc limit 5", nativeQuery = true)
    List<ReportResponseInterface> findSatisfactions(@Param("year") int year, @Param("memberId") Long memberId);

    // 구매 평균 만족도 조회(연별)
    @Query(value = "select avg(b.satisfaction) " +
            "from Board b " +
            "where year(b.date) = :year and b.member.id = :memberId ")
    Long findAvg(@Param("year") int year, @Param("memberId") Long memberId);
}
