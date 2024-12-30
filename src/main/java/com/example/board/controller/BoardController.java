package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoardController {
    @GetMapping("/board/boardlist")
    @ResponseBody
    public String boardlist() {
        return "게시글 리스트 보기";
    }
}
