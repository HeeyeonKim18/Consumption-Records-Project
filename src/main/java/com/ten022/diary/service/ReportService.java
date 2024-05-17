package com.ten022.diary.service;

import com.ten022.diary.domain.Member;
import com.ten022.diary.dto.report.ReportResponse;
import com.ten022.diary.exception.NotFoundException;
import com.ten022.diary.repository.BoardRepository;
import com.ten022.diary.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    /*
        월간 통계
     */
    public List<ReportResponse> findCategories(int year, int month, Long memberId) {

        Member member = getMember(memberId);
        return boardRepository.findCategories(year, month, member.getId())
                .stream()
                .map(ReportResponse::new)
                .collect(Collectors.toList());

    }

    public List<ReportResponse> findEmotions(int year, int month, long memberId) {
        Member member = getMember(memberId);
        return boardRepository.findEmotions(year, month, member.getId())
                .stream()
                .map(ReportResponse::new)
                .collect(Collectors.toList());
    }

    public List<ReportResponse> findFactors(int year, int month, long memberId) {
        Member member = getMember(memberId);
        return boardRepository.findFactors(year, month, member.getId())
                .stream()
                .map(ReportResponse::new)
                .collect(Collectors.toList());
    }

    public List<ReportResponse> findSatisfactions(int year, int month, long memberId) {
        Member member = getMember(memberId);
        return boardRepository.findSatisfactions(year, month, member.getId())
                .stream()
                .map(ReportResponse::new)
                .collect(Collectors.toList());
    }

    public Long findAvg(int year, int month, long memberId) {
        Member member = getMember(memberId);
        return boardRepository.findAvg(year, month, member.getId());
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("회원 정보가 존재하지 않습니다."));
    }

    /*
        연간 통계
     */
    public List<ReportResponse> findCategories(int year, Long memberId) {

        Member member = getMember(memberId);
        return boardRepository.findCategories(year, member.getId())
                .stream()
                .map(ReportResponse::new)
                .collect(Collectors.toList());

    }

    public List<ReportResponse> findEmotions(int year, long memberId) {
        Member member = getMember(memberId);
        return boardRepository.findEmotions(year, member.getId())
                .stream()
                .map(ReportResponse::new)
                .collect(Collectors.toList());
    }

    public List<ReportResponse> findFactors(int year, long memberId) {
        Member member = getMember(memberId);
        return boardRepository.findFactors(year, member.getId())
                .stream()
                .map(ReportResponse::new)
                .collect(Collectors.toList());
    }

    public List<ReportResponse> findSatisfactions(int year, long memberId) {
        Member member = getMember(memberId);
        return boardRepository.findSatisfactions(year, member.getId())
                .stream()
                .map(ReportResponse::new)
                .collect(Collectors.toList());
    }

    public Long findAvg(int year, long memberId) {
        Member member = getMember(memberId);
        return boardRepository.findAvg(year, member.getId());
    }


}
