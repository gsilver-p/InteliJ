package com.example.board.controller;

import com.example.board.dto.BoardDto;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    //@Autowired
    private final BoardService boardService;


    @GetMapping
    public String boardlist(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, Model model) {
        List<BoardDto> boardList = boardService.getBoardList(pageNum);
        if (boardList != null) {
            // 페이지 정보 가져오기
            String pageHtml=boardService.getPaging(pageNum);
            model.addAttribute("paging", pageHtml);
            model.addAttribute("boardList", boardList);
            return "board/boardlist";
        }
        return "redirect:/";
    }

    @GetMapping("/write")
    public String write() {
        return "board/write";
    }

    @PostMapping("/write")
    public String write(BoardDto boardDto) {
        // DB에 글을 저장
        return "redirect:/board/boardlist";
    }
}
