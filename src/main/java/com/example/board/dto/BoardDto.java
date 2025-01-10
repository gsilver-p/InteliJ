package com.example.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class BoardDto {   // view -> dto -> service class(dto -> entity) -> DB
    private int b_num;
    private String b_title;
    private String b_contents;
    private String b_writer;
    private LocalDateTime b_date;   // 다른 시간대로 변환 용이
    // private String b_date;  단순 출력용
    private int b_view;

    // 첨부파일 리스트 view --> DB
    private List<MultipartFile> attachments;  //mybatis의 ResultMap 활용
    // 글 상세보기 시 해당 글의 첨부리스트 (DB --> view)
    private List<BoardFile> boardFileList;

}