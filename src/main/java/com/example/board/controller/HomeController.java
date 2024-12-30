package com.example.board.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @GetMapping
    public String home(HttpServletRequest request, HttpSession session, Model model) {
        // 세션의 불필요한 속성 객체 즉시 삭제하거나 루트에서  삭제할 것.
        if(session.getAttribute("preUrl_login")!=null) {
            session.removeAttribute("preUrl_login");
        }
        return "index";
    }
}
