package com.example.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class MemberDto {
    private String m_id;  // 필드명==파라미터명==컬럼명 일치하게 하는게 좋아
    private String m_pw;
    private String m_name;
    private String m_birth;
    private String m_addr;
    private String m_point;

}
