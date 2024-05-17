package com.ten022.diary.dto.goal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoalResponse {
    private Long goalId;
    private Long memberId;
    private String msg;
    private int year;
    private int month;
}
