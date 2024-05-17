package com.ten022.diary.service;

import com.ten022.diary.domain.Board;
import com.ten022.diary.domain.Member;
import com.ten022.diary.domain.Storage;
import com.ten022.diary.domain.enumtype.Categories;
import com.ten022.diary.domain.enumtype.Emotions;
import com.ten022.diary.domain.enumtype.Factors;
import com.ten022.diary.dto.board.BoardRequest;
import com.ten022.diary.dto.board.BoardResponse;
import com.ten022.diary.dto.file.SavedImageResponse;
import com.ten022.diary.dto.file.UploadFileDto;
import com.ten022.diary.exception.NotFoundException;
import com.ten022.diary.repository.BoardRepository;
import com.ten022.diary.repository.MemberRepository;
import com.ten022.diary.repository.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final StorageService storageService;
    private final StorageRepository storageRepository;

    private Board getBoard(Long boardId) {
        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException("해당 게시물은 존재하지 않습니다"));
        return findBoard;
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("해당 멤버가 존재하지 않습니다."));
    }

    public BoardResponse findBoard(Long boardId) {
        Board findBoard = getBoard(boardId);
        return new BoardResponse(findBoard.getContents(), findBoard.getCategories(),
                findBoard.getFactors(), findBoard.getEmotions(), findBoard.getSatisfaction(), findBoard.getDate());

    }

    private URL getFilePath(Storage savedStorage) {
        return storageService.getFilePath(savedStorage.getStorageFileName());
    }

    @Transactional
    public SavedImageResponse insertPostingV1(Long memberId, MultipartFile multipartFile, BoardRequest request) throws IOException {

        // 엔티티 조회
        Member member = getMember(memberId);

        UploadFileDto uploadFileDto = storageService.uploadFile(multipartFile);

        Storage storage = getStorage(uploadFileDto);

        Board board = getBoard(request, member, storage);

        board.setMember(member);

        Board savedBoard = boardRepository.save(board);

        URL filePath = getFilePath(storage);

        return new SavedImageResponse(savedBoard.getId(), filePath);
    }

    @Transactional
    public SavedImageResponse updatePostingV1(Long boardId, MultipartFile multipartFile, BoardRequest request) throws IOException {
        Board findBoard = getBoard(boardId);

        if (multipartFile.isEmpty()) {
            new NotFoundException("수정할 첨부파일이 존재하지 않습니다");
        }

        // 여기부터
        Storage findStorage = storageRepository.findById(findBoard.getStorage().getId())
                .orElseThrow(() -> new NotFoundException("해당 이미지는 존재하지 않습니다"));

        UploadFileDto uploadFileDto = storageService.uploadFile(multipartFile);

        Storage newStorage = getStorage(uploadFileDto);

        Storage savedStorage = storageRepository.save(newStorage);
        storageRepository.delete(findStorage);
        storageService.deleteFile(findStorage.getStorageFileName());


        Long updateBoardId = findBoard.updateBoard(savedStorage, request.getCategories(),
                request.getEmotions(), request.getFactors(), request.getSatisfactions(), request.getContents(), LocalDate.now());

        URL filePath = getFilePath(savedStorage);

        return new SavedImageResponse(updateBoardId, filePath);
    }

    @Transactional
    public boolean deletePostingV1(Long boardId) {
        Board board = getBoard(boardId);
        boardRepository.delete(board);

        Storage storage = board.getStorage();
        storageService.deleteFile(storage.getStorageFileName());
        return true;
    }

    private static Board getBoard(BoardRequest request, Member member, Storage storage) {
        return Board.builder()
                .member(member)
                .categories(Categories.valueOf(request.getCategories()))
                .emotions(Emotions.valueOf(request.getEmotions()))
                .factors(Factors.valueOf(request.getFactors()))
                .satisfaction(request.getSatisfactions())
                .storage(storage)
                .contents(request.getContents())
                .date(LocalDate.now())
                .build();
    }

    private static Storage getStorage(UploadFileDto uploadFileDto) {
        return Storage.builder()
                .storageFileName(uploadFileDto.getStorageFileName())
                .originalFileName(uploadFileDto.getOriginalFileName())
                .build();
    }

    /**
     * 이미지 업로드 테스트용
     */
    @Transactional
    public String insertPostingOnlyImage(Long memberId, MultipartFile multipartFile) throws IOException {

        // 엔티티 조회
        Member member = getMember(memberId);

        UploadFileDto uploadFileDto = storageService.uploadFile(multipartFile);

        Storage storage = getStorage(uploadFileDto);

        return storage.getStorageFileName();
    }
}
