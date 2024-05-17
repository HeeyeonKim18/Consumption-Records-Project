package com.ten022.diary.dto.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavedImageResponse {
    private Long boardId;
    private URL imagePath;
}
