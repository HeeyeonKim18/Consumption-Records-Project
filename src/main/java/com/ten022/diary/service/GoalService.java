package com.ten022.diary.service;

import com.ten022.diary.domain.Goal;
import com.ten022.diary.domain.Member;
import com.ten022.diary.dto.goal.GoalRequest;
import com.ten022.diary.dto.goal.GoalResponse;
import com.ten022.diary.exception.NotFoundException;
import com.ten022.diary.repository.GoalRepository;
import com.ten022.diary.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoalService {

    private final GoalRepository goalRepository;
    private final MemberRepository memberRepository;

    /**
     * 현재 달 목표 조회
     *
     * @param memberId 사용자 아이디 값
     * @return 현재 목표
     */
    public String findGoal(Long memberId) {

        int year = getYear();
        int month = getMonth();

        Goal goal = goalRepository.findIdByYearAndMonthAndMember(year, month, memberId)
                .orElseThrow(() ->
                        new NotFoundException("이번 달 목표가 생성되지 않았습니다."));

        return goal.getMission();
    }

    /**
     * 목표 저장 및 수정
     *
     * @param memberId 사용자 아이디
     * @param request  사용자 목표
     * @return GoalResponse(memberId, goalId, msg, year, month)
     */
    @Transactional
    public GoalResponse insertGoal(Long memberId, GoalRequest request) {

        GoalResponse goalResponse = new GoalResponse();

        int year = getYear();
        int month = getMonth();

        Member member = getMember(memberId);

        Optional<Goal> goal = goalRepository.findIdByYearAndMonthAndMember(year, month, member.getId());


        if (goal.isEmpty()) {
            long newGoalId = newGoal(member, request, LocalDate.now());
            goalResponse.setGoalId(newGoalId);
            goalResponse.setMsg("이번 달 목표가 저장되었습니다.");
        } else {
            modifiedGoal(goal.get(), request);
            goalResponse.setGoalId(goal.get().getId());
            goalResponse.setMsg("이번 달 목표가 수정되었습니다.");
        }

        goalResponse.setYear(year);
        goalResponse.setMonth(month);
        goalResponse.setMemberId(member.getId());

        return goalResponse;
    }

    /**
     * 목표 저장 로직
     *
     * @param member
     * @param request
     * @param date
     * @return 목표 아이디 값
     */
    @Transactional
    public long newGoal(Member member, GoalRequest request, LocalDate date) {

        Goal goal = Goal.builder()
                .member(member)
                .date(date)
                .mission(request.getMission())
                .build();

        Goal savedGoal = goalRepository.save(goal);

        return savedGoal.getId();
    }

    /**
     * 목표 수정 로직
     *
     * @param goal
     * @param request
     */
    @Transactional
    public void modifiedGoal(Goal goal, GoalRequest request) {
        goal.updateGoal(request.getMission());
    }

    // 멤버 아이디 조회 메서드
    private Member getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new NotFoundException("요청하신 회원은 존재하지 않습니다."));
        return member;
    }

    // 서버 날짜(월) 값 가져오기
    private static int getMonth() {
        int month = LocalDate.now().getMonthValue();
        return month;
    }

    // 서버 날짜(년) 값 가져오기
    private static int getYear() {
        int year = LocalDate.now().getYear();
        return year;
    }
}
