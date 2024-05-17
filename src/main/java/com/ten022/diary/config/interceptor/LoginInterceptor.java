package com.ten022.diary.config.interceptor;

import com.ten022.diary.config.jwt.TokenProvider;
import com.ten022.diary.domain.Member;
import com.ten022.diary.exception.NotFoundException;
import com.ten022.diary.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PRIFIX = "Bearer ";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        String token = getAccessToken(authorizationHeader);

        if (tokenProvider.validToken(token)) {
            Long userId = tokenProvider.getUserId(token);
            Member member = memberRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException("사용자 아이디가 존재하지 않습니다. "));

            if (member.getId() == userId) {
                return true;
            }
        }
        throw new IllegalAccessException("유효하지 않은 토큰 값으로 접근할 수 없습니다.");
    }

    private String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PRIFIX)) {
            return authorizationHeader.substring(TOKEN_PRIFIX.length());
        }
        return null;
    }
}

