package com.example.board.dao;

// DB FrameWork : ibatis ---> mybatis
import com.example.board.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

// @Repository // 인터페이스라서 만들면 안돼
@Mapper
public interface MemberDao {
// @Select("select count(*) from member where m_id=#{m_id} and m_pw=#{m_pw}")  // 여기다 sql문(mybatis문법) 쓰자!
    boolean login(MemberDto memberDto);

    boolean join(MemberDto memberDto);

    boolean isUsedId(String mId);

    MemberDto getMemberInfo(String mId);

    String getSecurityPw(String mId);

    void updateMemberPoint(MemberDto memberDto);

    List<MemberDto> getAllMember();
}
