package com.example.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchDto {
    // 유효성 검사 spring validate
    private String colname;
    private String keyword;
    private Integer pageNum = 1;  // 현재 페이지 번호

    private Integer listCnt;  // 페이지 당 글 갯수
    private Integer startIndex; // listCnt 10개 일 때, 1page index 0~9개 / 2page index 10~19개

}
