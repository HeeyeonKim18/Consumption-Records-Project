package com.ten022.diary.controller;

import com.ten022.diary.common.ApiResponse;
import com.ten022.diary.dto.auth.OauthRequest;
import com.ten022.diary.exception.NotValidatedValueException;
import com.ten022.diary.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Map login(@RequestBody @Validated OauthRequest params, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            throw new NotValidatedValueException();
        }

        return authService.login(params);
    }

    @DeleteMapping("/{memberId}")
    public ApiResponse removeMember(@PathVariable("memberId") Long memberId){
        return ApiResponse.builder()
                .result("ok")
                .msg("회원 탈퇴 성공")
                .data(authService.deleteMember(memberId))
                .build();
    }

}