package com.example.board.controller;

import com.example.board.dto.BoardDto;
import com.example.board.dto.MemberDto;
import com.example.board.dto.ReplyDto;
import com.example.board.dto.SearchDto;
import com.example.board.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        if (searchdto.getPageNum() == null)
            searchdto.setPageNum(1);

        if (searchdto.getListCnt() == null)
            searchdto.setListCnt(BoardService.listcnt);

        if (searchdto.getStartIndex() == null)
            searchdto.setStartIndex(0);

        List<BoardDto> boardList = null;
        // 정적쿼리
        //  if(searchdto.getColname() == null || searchdto.getKeyword() == null) {
        //      boardList = boardService.getBoardList(searchdto.getPageNum()); // 페이지 번호 클릭
        //        } else {
        //            boardList = boardService.getBoardList(searchdto);
        //        }

            // 동적 쿼리 작성 시
        // boardList = boardService.getBoardListSearch(searchdto);  // IF 문
        boardList = boardService.getBoardListSearch(searchdto);  // CHOOSE WHEN 문
        if (boardList != null) {
            // 페이지 정보
            String pageHtml = boardService.getPaging(searchdto);
            model.addAttribute("paging", pageHtml);
            model.addAttribute("boardList", boardList);
            return "board/boardlist";
        }
        return "redirect:/";
    }

//    @GetMapping("/detail/{bno}")
//    public String detail(@PathVariable("bno") Integer bno, Model model) {
//        log.info("==== con detail bno:{}", bno);
//        return null;
//    }

    @GetMapping("/detail")
    public String detailParam(@RequestParam("b_num")Integer b_num, Model model) {
        log.info("==== con b_num:{}", b_num);
        if(b_num == null || b_num < 1) {return "redirect:/board";}
        BoardDto boardDto = boardService.getBoardDetail(b_num);
        log.info("==== boardDto:{}", boardDto);
        if(boardDto != null) {
            model.addAttribute("boardDto", boardDto);
            return "board/detail";
        } else {
            // ModelAndView mav = new ModelAndView();
            return "redirect:/board";
        }
    }

    @GetMapping("/delete")
    public String boardDelete(@RequestParam("b_num")Integer b_num, Model model, RedirectAttributes redirectAttributes) {
        log.info("==== Delete b_num:{}", b_num);
        if(b_num == null || b_num < 1) {
            return "redirect:/board";
        }
        if(boardService.boardDelete(b_num)) {
            redirectAttributes.addFlashAttribute("msg",b_num+"번 삭제 성공! 아쉽다..");  // 한 번 출력
           // redirectAttributes.addAttribute("msg",b_num+"번 삭제 성공! 아쉽다.."); // 리퀘스트 객체에 저장 여러 번 출력
            return "redirect:/board";
        } else {
            redirectAttributes.addFlashAttribute("msg",b_num+"번 삭제 실패^_^");
            return "redirect:/board/detail?b_num="+b_num;
        }
    }

    // 제이슨으로 안받을 때!!
//    @PostMapping("/reply")
//    @ResponseBody
//    public String insertReply(@RequestParam("r_bnum") Integer r_bnum,
//                              @RequestParam("r_contents") String r_contents,
//                              HttpSession session) {
//        log.info("==== insert r_bnum:{}", r_bnum);
//        log.info("==== insert r_contents:{}", r_contents);
//        String m_id = ((MemberDto)session.getAttribute("member")).getM_id();
//        log.info("==== insert r_writer:{}", m_id);
//        return "성공";
//
//    }

    // 제이슨으로 받을 때!
    @PostMapping("/reply")
    @ResponseBody
    public String insertReply(@RequestBody ReplyDto replyDto, HttpSession session) {
        log.info("==== insert r_bnum:{}", replyDto.getR_bnum());
        log.info("==== insert r_contents:{}", replyDto.getR_contents());
        String m_id = ((MemberDto)session.getAttribute("member")).getM_id();
        log.info("==== insert r_writer:{}", m_id);
        return "성공";
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
