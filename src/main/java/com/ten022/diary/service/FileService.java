package com.ten022.diary.service;

import com.ten022.diary.domain.Board;
import com.ten022.diary.domain.Member;
import com.ten022.diary.dto.file.ImageResponseV1;
import com.ten022.diary.exception.NotFoundException;
import com.ten022.diary.repository.BoardRepository;
import com.ten022.diary.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final StorageService storageService;

    public ImageResponseV1 getImageV1(Long boardId) throws MalformedURLException {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(
                        () -> new NotFoundException("해당 파일은 존재하지 않습니다."));

        URL fileFullPath = storageService.getFilePath(board.getStorage().getStorageFileName());

        return new ImageResponseV1(board.getId(),fileFullPath);

    }

    public List<ImageResponseV1> getAllImagesV1(int year, int month, long memberId) {

        List<ImageResponseV1> storage = new ArrayList<>();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("없음"));

        List<Board> board = member.getBoard();

        for (Board getBoard : board) {
            if (getBoard.getDate().getYear() == year && getBoard.getDate().getMonthValue() == month) {
                if(getBoard.getStorage().getStorageFileName()!= null) {
                    URL fileFullPath = storageService.getFilePath(getBoard.getStorage().getStorageFileName());
                    storage.add(new ImageResponseV1(getBoard.getId(),fileFullPath));
                }
            }
        }

        return storage;
    }
}
