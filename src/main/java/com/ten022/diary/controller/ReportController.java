package com.ten022.diary.controller;

import com.ten022.diary.common.ApiResponse;
import com.ten022.diary.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    /**
     * 월별 통계 자료
     * @param year
     * @param month
     * @param memberId
     * @return
     */

    // 구매 카테고리 조회(월별)
    @GetMapping("/categories/{year}/{month}/{memberId}")
    public ApiResponse getCategories(@PathVariable("year") int year,
                                     @PathVariable("month") int month,
                                     @PathVariable("memberId") Long memberId) {

        return ApiResponse.builder()
                .result("ok")
                .msg("구매 카테고리 월별 자료를 조회합니다.")
                .data(reportService.findCategories(year, month, memberId))
                .build();

    }

    // 구매 감정 조회(월별)
    @GetMapping("/emotions/{year}/{month}/{memberId}")
    public ApiResponse getEmotions(@PathVariable("year") int year,
                                   @PathVariable("month") int month,
                                   @PathVariable("memberId") long memberId) {

        return ApiResponse.builder()
                .result("ok")
                .msg("구매 감정 월별 자료를 조회합니다.")
                .data(reportService.findEmotions(year, month, memberId))
                .build();

    }

    // 구매 요인 상위 5개 조회(월별)
    @GetMapping("/factors/{year}/{month}/{memberId}")
    public ApiResponse getFactors(@PathVariable("year") int year,
                                  @PathVariable("month") int month,
                                  @PathVariable("memberId") long memberId) {

        return ApiResponse.builder()
                .result("ok")
                .msg("구매 요인 상위 5개 월별 자료를 조회합니다.")
                .data(reportService.findFactors(year, month, memberId))
                .build();

    }

    // 구매 만족도 상위 5개 조회(월별)
    @GetMapping("/satisfactions/{year}/{month}/{memberId}")
    public ApiResponse getSatisfactions(@PathVariable("year") int year,
                                        @PathVariable("month") int month,
                                        @PathVariable("memberId") long memberId) {

        return ApiResponse.builder()
                .result("ok")
                .msg("구매 만족도 상위 5개 월별 자료를 조회합니다.")
                .data(reportService.findSatisfactions(year, month, memberId))
                .build();

    }

    // 구매 만족도 평균 조회(월별)
    @GetMapping("/satisfactions/avg/{year}/{month}/{memberId}")
    public ApiResponse getAvg(@PathVariable("year") int year,
                              @PathVariable("month") int month,
                              @PathVariable("memberId") long memberId) {

        return ApiResponse.builder()
                .result("ok")
                .msg("구매 평균 만족도 월별 자료를 조회합니다.")
                .data(reportService.findAvg(year, month, memberId))
                .build();

    }

    /**
     * 연별 통계 자료
     * @param year
     * @param memberId
     * @return
     */

    // 구매 카테고리 조회(연별)
    @GetMapping("/categories/{year}/{memberId}")
    public ApiResponse getCategories(@PathVariable("year") int year,
                                     @PathVariable("memberId") Long memberId) {

        return ApiResponse.builder()
                .result("ok")
                .msg("구매 카테고리 연별 자료를 조회합니다.")
                .data(reportService.findCategories(year, memberId))
                .build();

    }

    // 구매 감정 조회(연별)
    @GetMapping("/emotions/{year}/{memberId}")
    public ApiResponse getEmotions(@PathVariable("year") int year,
                                   @PathVariable("memberId") long memberId) {

        return ApiResponse.builder()
                .result("ok")
                .msg("구매 감정 연별 자료를 조회합니다.")
                .data(reportService.findEmotions(year, memberId))
                .build();

    }

    // 구매 요인 상위 5개 조회(연별)
    @GetMapping("/factors/{year}/{memberId}")
    public ApiResponse getFactors(@PathVariable("year") int year,
                                  @PathVariable("memberId") long memberId) {

        return ApiResponse.builder()
                .result("ok")
                .msg("구매 요인 상위 5개 연별 자료를 조회합니다.")
                .data(reportService.findFactors(year, memberId))
                .build();

    }

    // 구매 만족도 상위 5개 조회(연별)
    @GetMapping("/satisfactions/{year}/{memberId}")
    public ApiResponse getSatisfactions(@PathVariable("year") int year,
                                        @PathVariable("memberId") long memberId) {

        return ApiResponse.builder()
                .result("ok")
                .msg("구매 만족도 상위 5개 연별 자료를  조회합니다.")
                .data(reportService.findSatisfactions(year, memberId))
                .build();

    }

    // 구매 만족도 평균 조회(연별)
    @GetMapping("/satisfactions/avg/{year}/{memberId}")
    public ApiResponse getAvg(@PathVariable("year") int year,
                              @PathVariable("memberId") long memberId) {

        return ApiResponse.builder()
                .result("ok")
                .msg("구매 평균 만족도 연별 자료를 조회합니다.")
                .data(reportService.findAvg(year, memberId))
                .build();

    }
}
