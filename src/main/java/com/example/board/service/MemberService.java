package com.example.board.service;

import com.example.board.dao.MemberDao;
import com.example.board.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    // @Autowired
    private final MemberDao memberdao;
    public boolean login(MemberDto memberDto) {
        return memberdao.login(memberDto);
    }

    public boolean join(MemberDto memberDto) {
        // 이미 사용 중인 ID가 있으면 True, 아니면 False
        if(memberdao.isUsedId(memberDto.getM_id())) {
            return false;  // 회원가입 실패
        }
        return memberdao.join(memberDto);  // 회원가입 성공 시 true, 실패 시 false
    }
}
