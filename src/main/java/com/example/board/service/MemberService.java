package com.example.board.service;

import com.example.board.dao.MemberDao;
import com.example.board.dto.MemberDto;
import com.example.board.exception.IdCheckException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    // @Autowired
    private final MemberDao memberdao;

    public MemberDto login(MemberDto memberDto) {
        String encodePw = memberdao.getSecurityPw(memberDto.getM_id());
        log.info("encodePw: "+encodePw);
        if(encodePw != null) {
            log.info("★★아이디가 존재해★★");
            BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
            if(pwEncoder.matches(memberDto.getM_pw(), encodePw)) {
                log.info("★로그인 성공★");
                return memberdao.getMemberInfo(memberDto.getM_id());
            } else {
                log.info("ㅠㅠ비밀번호 일치하지 않아ㅠㅠ");
                return null;
            }
        } else {
            log.info("ㅠㅠ아이디 없어ㅠㅠ");
            return null;
        }
    }

    public boolean join(MemberDto memberDto) {
        // 이미 사용 중인 ID가 있으면 True, 아니면 False
        if(memberdao.isUsedId(memberDto.getM_id())) {
            return false;  // 회원가입 실패
        }
        // Encoder(암호화) <---> Decoder(복호화) : 스프링은 암호화만 지원해줘
        BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
        memberDto.setM_pw(pwEncoder.encode(memberDto.getM_pw()));
        return memberdao.join(memberDto);  // 회원가입 성공 시 true, 실패 시 false
    }

    public boolean isUsedId(String mId) {
        if(memberdao.isUsedId(mId)) {
            throw new IdCheckException("이미!!!! 사용 중인 아이디야");
        } else {
            return false;
        }
    }

    public Map<String, Object> testParam2(String id, int point) {
        return memberdao.testParam2(id,point);
    }
}
