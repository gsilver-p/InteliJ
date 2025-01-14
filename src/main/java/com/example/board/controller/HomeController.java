package com.example.board.controller;

import com.example.board.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.experimental.PackagePrivate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class HomeController {
    private final MemberService memberService;

    public HomeController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String home(HttpServletRequest request, HttpSession session, Model model) {
        // 세션의 불필요한 속성 객체 즉시 삭제하거나 루트에서  삭제할 것.
        if (session.getAttribute("preUrl_login") != null) {
            session.removeAttribute("preUrl_login");
        }
        return "index";
    }

    // DB에 파라미터 2개 전달하기
    @GetMapping("/test/{id}/{point}")
    @ResponseBody
    public Map<String, Object> test(@PathVariable String id, @PathVariable int point) {
        log.info("id:{},point:{}", id, point);
        Map<String, Object> map = new HashMap<String, Object>();
        map = memberService.testParam2(id, point);
        if (map != null) {
            map.put("msg", "success");
            return map;
        }
        return Map.of("msg","검색 실패");
    }
}

