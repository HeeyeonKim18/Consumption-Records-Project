package com.ten022.diary.controller;

import com.ten022.diary.common.ApiResponse;
import com.ten022.diary.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class FileController {

    private final FileService fileService;

    @GetMapping("/{boardId}")
    public ApiResponse showImage(@PathVariable("boardId") Long boardId) throws MalformedURLException {
        return ApiResponse.builder()
                .result("ok")
                .msg(boardId + "의 경로를 가져옵니다")
                .data(fileService.getImageV1(boardId))
                .build();
    }

    @GetMapping( "/{year}/{month}/{memberId}")
    public ApiResponse showImages(@PathVariable("year") int year,
                                  @PathVariable("month") int month,
                                  @PathVariable("memberId") long memberId) {
        return ApiResponse.builder()
                .result("ok")
                .msg("모든 이미지 경로를 가져옵니다")
                .data(fileService.getAllImagesV1(year, month, memberId))
                .build();
    }
}
