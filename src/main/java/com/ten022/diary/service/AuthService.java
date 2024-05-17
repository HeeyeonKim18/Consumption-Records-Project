package com.ten022.diary.service;

import com.ten022.diary.config.jwt.TokenProvider;
import com.ten022.diary.dto.auth.OauthRequest;
import com.ten022.diary.domain.Member;
import com.ten022.diary.exception.NotFoundException;
import com.ten022.diary.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    public Map<String, Object> login(OauthRequest params) {
        Long memberId = findOrCreateMember(params);
        String token = tokenProvider.generateToken(memberId);
        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("accessToken", token);
        result.put("memberId", memberId);
        return result;
    }

    private Long findOrCreateMember(OauthRequest request) {
        return memberRepository.findByEmail(request.getEmail())
                .map(Member::getId)
                .orElseGet(() -> newMember(request));
    }

    private Long newMember(OauthRequest request) {
        Member member = Member.builder()
                .email(request.getEmail())
                .name(request.getName())
                .build();

        Long savedId = memberRepository.save(member).getId();

        if (savedId == null) {
//            throw new NotFoundException("회원 정보가 저장되지 않았습니다.");
        }

        return memberRepository.save(member).getId();
    }

    public Long deleteMember(long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("해당 멤버는 존재하지 않습니다"));

        memberRepository.delete(member);
        return member.getId();
    }
}