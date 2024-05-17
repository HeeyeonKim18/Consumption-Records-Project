package com.ten022.diary.controller;

import com.ten022.diary.common.ApiResponse;
import com.ten022.diary.dto.goal.GoalRequest;
import com.ten022.diary.dto.goal.GoalResponse;
import com.ten022.diary.exception.NotValidatedValueException;
import com.ten022.diary.service.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/goal")
public class GoalController {

    private final GoalService goalService;

    // 목표 조회
    @GetMapping("/{memberId}")
    public ApiResponse findGoal(@PathVariable("memberId") Long id) {
        return ApiResponse.builder()
                .result("ok")
                .msg("이번 달 목표가 조회되었습니다.")
                .data(goalService.findGoal(id)).build();
    }

    // 목표 저장 및 수정
    @PostMapping("/{memberId}")
    public GoalResponse saveGoal(@PathVariable("memberId") Long id,
                                 @RequestBody @Validated GoalRequest request,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new NotValidatedValueException("요청한 값이 올바르지 않습니다.");
        }

        return goalService.insertGoal(id, request);
    }
}
