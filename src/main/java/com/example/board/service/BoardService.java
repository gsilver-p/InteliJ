package com.example.board.service;

import com.example.board.common.FileManager;
import com.example.board.common.Paging;
import com.example.board.dao.BoardDao;
import com.example.board.dao.MemberDao;
import com.example.board.dto.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {
    public static final Integer listcnt = 10;
    public static final Integer PAGECOUNT = 2;

    private final BoardDao boardDao;
    private final MemberDao memberDao;
    private final FileManager fm;

    public List<BoardDto> getBoardList(Integer pageNum) {
        // select * from board order b_date desc limit 0,10  , 1page
        // select * from board order b_date desc limit 10,10  , 2page
        // select * from board order b_date desc limit 20,10  , 3page
        Map<String, Integer> pageMap = new HashMap<>();
        pageMap.put("startIndex", (pageNum - 1) * 10);
        pageMap.put("listCnt", 10);
        return boardDao.getBoardList(pageMap);
    }

    public String getPaging(SearchDto searchdto) {
        int totalNum = boardDao.getBoardCount(searchdto);
        log.info("totalNum=" + totalNum);
        String listUrl = null;
        if (searchdto.getColname() != null) {
            listUrl = "/board?colname=" + searchdto.getColname() + "&keyword=" + searchdto.getKeyword() + "&";
        } else {
            listUrl = "/board?";
        }
        Paging paging = new Paging(totalNum, searchdto.getPageNum(), listcnt, PAGECOUNT, listUrl);
        return paging.makeHtmlPaging();
    }

    // 검색을 통한 게시글 리스트
    public List<BoardDto> getBoardList(SearchDto searchdto) {
        Integer pageNum = searchdto.getPageNum();
        searchdto.setStartIndex((pageNum - 1) * BoardService.listcnt);
        return boardDao.getBoardListSearch(searchdto);  // 검색결과가 없거나 실패시 null 반환
    }

    public List<BoardDto> getBoardListSearch(SearchDto searchdto) {
        Integer pageNum = searchdto.getPageNum();
        searchdto.setStartIndex((pageNum - 1) * searchdto.getListCnt());
        log.info("pageNum=" + searchdto);
        return boardDao.getBoardListSearch(searchdto);
    }

    public List<BoardDto> getBoardListSearchNew(SearchDto searchdto) {
        Integer pageNum = searchdto.getPageNum();
        searchdto.setStartIndex((pageNum - 1) * searchdto.getListCnt());
        return boardDao.getBoardListSearchNew(searchdto);
    }

    public BoardDto getBoardDetail(Integer b_num) {
        // return boardDao.getBoardDetail(b_num);
        return boardDao.getBoardDetailWithFiles(b_num);
    }

    @Transactional  // 수동커밋으로 바꾸는 친구
    public boolean boardDelete(Integer b_num, HttpSession session) {
        // 1. 자식 데이터들 삭제(첨부파일, 댓글)
        // 2. 부모 데이터 삭제
        // 3. 전체 진행상황이 삭제면 commit, 하나라도 실패하면 rollback
        List<ReplyDto> replyList = boardDao.getReplyList(b_num);
        if (replyList != null && replyList.size() > 0) {
            if (!boardDao.deleteReply(b_num)) {
                log.info("!!!!! deleteReply 예외발생");
                throw new RuntimeException(); // rollback
            }
        }
        String[] sysfiles = boardDao.getSysFileName(b_num);
        if (sysfiles != null && sysfiles.length > 0) {
            if (!boardDao.deleteBoardFile(b_num)) {
                log.info("!!!! 첨부파일 삭제 예외");
                throw new RuntimeException();
            }
        }
        if (!boardDao.boardDelete(b_num)) {
            log.info("!!!! 글 삭제 예외");
            throw new RuntimeException();
        }
        // 4. upload 폴더 파일 삭제
        if (sysfiles.length > 0) {
            fm.fileDelte(sysfiles, session);
        }
        return true;
    }

public boolean boardWrite(BoardDto boardDto, HttpSession session) {
    // 1. 글번호(100), 글제목, 글내용, 글쓴이, ... insert!
    // 1-1. select 글번호! (1, 1-1 동시진행)
    // 2. 첨부파일이 존재한다면 글번호(100), 원파일명, 난수파일명 insert!
    boolean result = boardDao.boardWriteSelectKey(boardDto);  //insert하면서 select도 같이!
    log.info("새 글 번호:{}", boardDto.getB_num());
    if (result) {
        // 글 쓸 때마다 point 10점 부여
        MemberDto memberDto = (MemberDto) session.getAttribute("member");
        int point = memberDto.getM_point() + 10;
        if (point > 999) {
            point = 999;
        }
        memberDto.setM_point(point);
        memberDao.updateMemberPoint(memberDto);
        MemberDto member = memberDao.getMemberInfo(memberDto.getM_id()); // 회원의 최신정보 가져오자!
        session.setAttribute("memberDto", member);
        // 첨부파일 여부 확인
        if (!boardDto.getAttachments().get(0).isEmpty()) {
            // 파일 업로드하고, DB insert
            if (fm.fileUpload(boardDto.getAttachments(), session, boardDto.getB_num())) {
                log.info("★upload ok!");
                return true;
            }
        }
        return true; // 첨부파일 없이 글쓰기만 성공!
    } else {
        return false;  // 글쓰기 실패야
    }
}

public List<BoardFile> getBoardFileList(Integer bNum) {
    return boardDao.getBoardFileList(bNum);
}

public List<ReplyDto> insertReply(ReplyDto replyDto) {
    List<ReplyDto> replyList = null;
    if (boardDao.insertReply(replyDto)) {
        replyList = boardDao.getReplyList(replyDto.getR_bnum());
    }
    return replyList;
}

public List<ReplyDto> getReplyList(Integer bNum) {
    return boardDao.getReplyList(bNum);
}
}
