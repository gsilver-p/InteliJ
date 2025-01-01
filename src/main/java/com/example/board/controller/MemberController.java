package com.example.board.controller;

import com.example.board.dto.MemberDto;
import com.example.board.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@Slf4j
@RequiredArgsConstructor // 필수 필드 생성자 - 생성자에 final 넣어주자
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private final MemberService memberservice;  // <--- DAO 객체

    @GetMapping("/login")
    public String loginForm(HttpSession session) {
        if(session.getAttribute("member") != null) {
            return "redirect:/";
        }
        return "member/login";
    }

    @PostMapping("/login")
    public String login(MemberDto memberDto, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        log.info("id:{},pw:{}", memberDto.getM_id(), memberDto.getM_pw());
        // DB에서 select!
        // MemberDto memberDto = new MemberDto();
        // memberDto.setM_id(m_id).setM_pw(m_pw);
        // boolean result = memberservice.login(memberDto);
        // if (result) {
            // session.setAttribute("id", memberDto.getM_id());
            // session.setAttribute("member", memberDto);
            // return "redirect:/";
        //
        // }
        // return "index";

        // 비밀번호 암호 화 이후!
        MemberDto member = memberservice.login(memberDto);
        log.info("member: {}",member);
        if(member != null) {
            session.setAttribute("member", member);  // id, name, point 담아왔지
            Object url = session.getAttribute("preUrl_login");
            if(url != null) {
                session.removeAttribute("preUrl_login");
                return "redirect: "+url.toString();
            }
            return "redirect:/board";
        }
        redirectAttributes.addFlashAttribute("msg","로그인 실패야ㅠ_ㅠ");
        return "redirect:/";
    }
    @GetMapping("/join")  // 프론트에서 get으로 넘기면 포워딩하거나 select 하는거!
    public String joinForm(HttpSession session) {
         // 로그인 인가여부는 너무 많이 확인해야해서 불편해  --> interceptor or spring security 활용
                if(session.getAttribute("member") != null) {
                    return "redirect:/";
                }
        return "member/join";
    }

    @PostMapping("/join")
    public String join(MemberDto memberDto, Model model, RedirectAttributes rttr) {
        log.info(memberDto.toString());
        boolean result = memberservice.join(memberDto);
        if(result) {
           // model.addAttribute("msg","가입 축하 빵빵");
            rttr.addFlashAttribute("msg","가입 축하 빵빵");
            // return "/member/login"; // 이건 디스패쳐 포워딩
            return "redirect:/";  // localhost / -- > index로 가는거 리다이렉트 포워딩
        }
        rttr.addFlashAttribute("msg","가입 실패야ㅠ_ㅠ");
        return "redirect:/member/join";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes rttr) {
        session.invalidate();
        rttr.addFlashAttribute("msg","친구야 잘가");
        return "redirect:/";
    }
}
