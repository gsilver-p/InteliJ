package com.example.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 테이블과 일치하는 클래스(entity) 우리는 dto 겸 entity 로 사용
@Data
@AllArgsConstructor
@NoArgsConstructor  //mybatis의 resultType, @ModelAttribute에서는 반드시 디폴트 생성자가 필요해!!
public class BoardFile {
    private long bf_num;
    private long bf_bnum;
    private String bf_orifilename;
    private String bf_sysfilename;
}
