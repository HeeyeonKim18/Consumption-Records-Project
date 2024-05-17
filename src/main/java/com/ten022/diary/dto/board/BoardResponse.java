package com.ten022.diary.dto.board;

import com.ten022.diary.domain.enumtype.Categories;
import com.ten022.diary.domain.enumtype.Emotions;
import com.ten022.diary.domain.enumtype.Factors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponse {

    private String contents;
    private Categories categories;
    private Factors factors;
    private Emotions emotions;
    private Long satisfactions;
    private LocalDate createdDate;
}
