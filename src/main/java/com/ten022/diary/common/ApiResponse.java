package com.ten022.diary.common;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
public class ApiResponse {
    private String result;
    private String msg;
    private Object data;

}