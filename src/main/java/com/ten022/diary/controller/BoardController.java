package com.ten022.diary.controller;

import com.ten022.diary.common.ApiResponse;
import com.ten022.diary.dto.board.BoardRequest;
import com.ten022.diary.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    // 포스팅 추가
    @PostMapping("/posting/{memberId}")
    public ApiResponse savePosting(@PathVariable("memberId") Long memberId,
                                   @RequestParam MultipartFile file,
                                   @RequestParam String contents,
                                   @RequestParam String categories,
                                   @RequestParam String emotions,
                                   @RequestParam String factors,
                                   @RequestParam Long satisfactions) throws IOException {

        BoardRequest request = new BoardRequest(contents, categories, factors, emotions, satisfactions);

        return ApiResponse.builder()
                .result("ok")
                .msg("포스팅이 저장되었습니다.")
                .data(boardService.insertPostingV1(memberId, file, request))
                .build();
    }

    // 포스팅 수정
    @PatchMapping("/posting/{boardId}")
    public ApiResponse modifiedPosting(@PathVariable("boardId") Long boardId,
                                       @RequestParam MultipartFile file,
                                       @RequestParam String contents,
                                       @RequestParam String categories,
                                       @RequestParam String emotions,
                                       @RequestParam String factors,
                                       @RequestParam Long satisfactions) throws IOException {

        BoardRequest request = new BoardRequest(contents, categories, factors, emotions, satisfactions);
        return ApiResponse.builder()
                .result("ok")
                .msg("포스팅이 수정되었습니다.")
                .data(boardService.updatePostingV1(boardId, file, request))
                .build();
    }

    // 포스팅 삭제
    @DeleteMapping("/posting/{boardId}")
    public ApiResponse deletePosting(@PathVariable("boardId") Long boardId) {
        return ApiResponse.builder()
                .result("ok")
                .msg("포스팅이 삭제되었습니다.")
                .data(boardService.deletePostingV1(boardId))
                .build();
    }

    // 포스팅 특정 포스팅 조회
    @GetMapping("/posting/{boardId}")
    public ApiResponse findBoard(@PathVariable("boardId") Long boardId){
        return ApiResponse.builder()
                .result("ok")
                .msg( boardId + "의 포스트를 조회합니다.")
                .data(boardService.findBoard(boardId))
                .build();
    }

    /**
     * 이미지 업로드 테스트용
     */
    @PostMapping("/posting/image/{memberId}")
    public ApiResponse insertPostingOnlyImage(@PathVariable("memberId") Long memberId,
                                   @RequestParam MultipartFile file) throws IOException {

        return ApiResponse.builder()
                .result("ok")
                .msg("포스팅이 저장되었습니다.")
                .data(boardService.insertPostingOnlyImage(memberId, file))
                .build();

    }
}