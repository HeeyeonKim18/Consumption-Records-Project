package com.ten022.diary.dto.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {

    private String keyword;
    private Long value;


    public ReportResponse(ReportResponseInterface reportResponseInterface) {
        this.keyword = reportResponseInterface.getKeyword();
        this.value = reportResponseInterface.getValue();
    }
}
