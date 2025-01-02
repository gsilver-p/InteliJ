package com.example.board.controller;

import com.example.board.dto.BoardDto;
import com.example.board.dto.SearchDto;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequestMapping("/board")
public class BoardController {
    //@Autowired
    private final BoardService boardService;


//    @GetMapping
//    public String boardlist(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, Model model) {
//        List<BoardDto> boardList = boardService.getBoardList(pageNum);
//        if (boardList != null) {
//            // 페이지 정보 가져오기
//            String pageHtml=boardService.getPaging(pageNum);
//            model.addAttribute("paging", pageHtml);
//            model.addAttribute("boardList", boardList);
//            return "board/boardlist";
//        }
//        return "redirect:/";
//    }

    @GetMapping
    public String boardlist(SearchDto searchdto, Model model) {
        log.info("before searchdto:{}", searchdto);
        if(searchdto.getPageNum() == null)
            searchdto.setPageNum(1);

        if(searchdto.getListCnt() == null)
            searchdto.setListCnt(BoardService.listcnt);

        if(searchdto.getStartIndex() == null)
            searchdto.setStartIndex(0);

        List<BoardDto> boardList = null;
        if(searchdto.getColname() == null || searchdto.getKeyword() == null) {
            boardList = boardService.getBoardList(searchdto.getPageNum()); // 페이지 번호 클릭
        } else {
            boardList = boardService.getBoardList(searchdto);
        }

        if (boardList != null) {
            String pageHtml=boardService.getPaging(searchdto.getPageNum());
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
