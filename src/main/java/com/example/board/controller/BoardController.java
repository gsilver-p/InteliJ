package com.example.board.controller;

import com.example.board.dto.*;
import com.example.board.exception.PageNumException;
import com.example.board.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.util.Enumeration;
import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/board")
public class BoardController {
    //@Autowired
    private final BoardService boardService;

    @GetMapping("/viewimg")
    public String viewimg(Model model) {
        return "board/viewimg";
    }

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
    public String boardlist(SearchDto searchdto, Model model, HttpSession session) {
        log.info("before searchdto:{}", searchdto);
        if (searchdto.getPageNum() == null || searchdto.getPageNum() < 1) {
            throw new PageNumException("잘못 된 페이지, 확인 바랍니다.");
        }
        if (searchdto.getListCnt() == null) {
            searchdto.setListCnt(BoardService.listcnt);
        }
        if (searchdto.getStartIndex() == null) {
            searchdto.setStartIndex(0);
        }
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

            // 상세보기에서 게시글 목록으로 돌아가는 방법!
            if (searchdto.getColname() != null) {
                session.setAttribute("searchdto", searchdto);
                log.info("★ 검색중이었다면~? searchdto 세션에 저장!");
            } else {
                session.removeAttribute("searchdto");
                session.setAttribute("pageNum", searchdto.getPageNum());
            }
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
    public String detailParam(@RequestParam("b_num") Integer b_num, Model model) {
        log.info("==== con b_num:{}", b_num);
        if (b_num == null || b_num < 1) {
            return "redirect:/board";
        }
        BoardDto boardDto = boardService.getBoardDetail(b_num);
        log.info("!!!!!!!!! boardDto:{}", boardDto);  // 제목, 내용, 글쓴이, 날짜, 파일리스트
        log.info("!!!!!!!!! boardFileList:{}", boardDto.getBoardFileList()); // 파일리스트만 확인하자!
        if (boardDto != null) {
            // 댓글 리스트 가져오기! (동기 or 비동기)
//             List<ReplyDto> rList = boardService.getReplyList(b_num);
            // 첨부파일 리스트 가져오기 (원글+파일리스트 한번에 다 받고나서는 필요없어!)
//             List<BoardFile> boardFileList = boardService.getBoardFileList(b_num);
            // model.addAttribute("boardFileList", boardFileList);
            // log.info("★★★★★ boardfileList:{},{}", boardFileList.size(), boardFileList);
            model.addAttribute("boardDto", boardDto);
            return "board/detail";
        } else {
            // ModelAndView mav = new ModelAndView();
            return "redirect:/board";
        }
    }

    @GetMapping("/delete")
    public String boardDelete(@RequestParam("b_num") Integer b_num, HttpSession session, RedirectAttributes redirectAttributes) {
        log.info("==== Delete b_num:{}", b_num);
        if (b_num == null || b_num < 1) {
            return "redirect:/board";
        }
        // try{
        boardService.boardDelete(b_num, session);
        redirectAttributes.addFlashAttribute("msg", b_num + "번 삭제 성공! 아쉽다..");  // 한 번 출력
        // redirectAttributes.addAttribute("msg",b_num+"번 삭제 성공! 아쉽다.."); // 리퀘스트 객체에 저장 여러 번 출력
        return "redirect:/board";
        // } catch (Exception e) {
        //    log.info("!!!!!!delete Board 실패");
        //    redirectAttributes.addFlashAttribute("msg", b_num + "번 삭제 실패^_^");
        //    return "redirect:/board/detail?b_num=" + b_num;
        //}
    }


    @GetMapping("/write")
    public String write() {
        return "board/write";
    }

//    @PostMapping("/write")
//    public String write(BoardDto boardDto,@RequestPart List<MultipartFile> attachments) {
//        log.info("★ write boardDto:{}", boardDto);
//        log.info("write attachments:{}", attachments.size());
//        for(MultipartFile file: attachments) {
//        log.info("★ write attachments:{}", attachments.getOriginalFilename());
//        }
//        return "redirect:/board";
//    }

    @PostMapping("/write")
    public String write(BoardDto boardDto, HttpSession session, RedirectAttributes redirectAttributes) {
        // tomcat rootPath: main/webapp
        // realPath: main/webapp/...

        // 내가 가지고 있는 파일이름과 같은 이름으로 다른 사람이 업로드하게 될 경우 덮어씌워지니까 각 ID를 부여해줘야해!(그건 내가 만드는거)
//        String realPath = session.getServletContext().getRealPath("/");
//        log.info("realPath:{}", realPath);
//        realPath += "upload/";
//        log.info("realPath:{}", realPath);
//        File dir = new File(realPath);
//        if(!dir.exists()) {
//            dir.mkdir();
//        }
        log.info("★ write boardDto: {}", boardDto);
        log.info("★ write boardDto attachments.size :{}", boardDto.getAttachments().size());
        for (MultipartFile file : boardDto.getAttachments()) {
            log.info("file:{}", file.getOriginalFilename());
            log.info("file.getSize():{}", file.getSize());  // byte단위 사이즈
            log.info("file.getSize():{}", file.isEmpty());
        }
        boolean result = boardService.boardWrite(boardDto, session);
        if (result) {
            redirectAttributes.addFlashAttribute("msg", "글쓰기 성공!");
            return "redirect:/board";
        } else {
            redirectAttributes.addFlashAttribute("msg", "글쓰기 실패ㅠㅠ");
            return "redirect:/board/write";
        }
    }

    @GetMapping("/update")
    public String update(Integer b_num, Model model) {
        log.info("=== 글 수정창 열기");
        BoardDto boardDto = boardService.getBoardDetail(b_num); // 원글 + 파일리스트
        if (boardDto != null) {
            log.info("===boardDto:{}", boardDto);
            model.addAttribute("boardDto", boardDto); // 원글
            return "board/update";
        } else {
            return "redirect:/board";
        }
    }
//    @PostMapping("/update")
//    public String update(BoardDto boardDto, Model model) {
//
//    
}
