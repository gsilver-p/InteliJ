package com.example.board.dao;

import com.example.board.dto.MemberDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MemberDaoTest {
    @Autowired  // jUnit에서는 생성자주입X 필드주입만 가능
    private MemberDao memberDao;

    // jUnit: 단위(클래스) 테스트 라이브러리
    @Test
    @DisplayName("회원가입 테스트")
    @Transactional  // 테스트가 끝나면 자동으로 rollback해줘~!(@Test랑 같이 쓸 때만)
    void joinTest() {
        MemberDto memberDto = new MemberDto();
        memberDto.setM_id("apfhdapfhd").setM_pw("1111").setM_name("메롱").setM_birth("20111111").setM_addr("개성");
        assertEquals(true,memberDao.join(memberDto));
    }

    @DisplayName("회원리스트 반환 테스트")
    @Test
    public void getAllMememberTest() {
        List<MemberDto> memberList = memberDao.getAllMember();
        assertThat(memberList.size()).isEqualTo(10);
        assertThat(memberList.size()).isLessThan(20);
    }
}
