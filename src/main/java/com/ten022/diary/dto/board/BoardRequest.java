package com.ten022.diary.dto.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequest {

    private String contents;
    private String categories;
    private String factors;
    private String emotions;
    private Long satisfactions;


}
