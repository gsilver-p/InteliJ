package com.example.board.controller;

import com.example.board.common.FileManager;
import com.example.board.dto.BoardFile;
import com.example.board.dto.ReplyDto;
import com.example.board.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController // @ResponseBody가 기본 값
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/board")
public class BoardRestController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private FileManager fm;

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
    // @ResponseBody   // jackson(메시지컨버터) : json --> java 객체변환 @RequestBody <-> @ResponseBody
    public ReplyDto insertReply(@RequestBody ReplyDto replyDto, HttpSession session, HttpServletRequest request) {
        System.out.println("replyDto : " + replyDto);
        log.info("replyDto:{}", replyDto);
        return replyDto;
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download(BoardFile boardFile, HttpSession session) {
        log.info("====orginfilename:{}", boardFile.getBf_orifilename());
        log.info("====sysfilename:{}", boardFile.getBf_sysfilename());
        // 다운로드 메소드 호출 ---> 브라우저 다운로드 됨
        try {
            ResponseEntity<Resource> resp = fm.fileDownload(boardFile, session);
            return resp;
        } catch (IOException e) {
            log.info("*****파일 다운로드 예외");
            throw new RuntimeException(e);
        }
    }
}
