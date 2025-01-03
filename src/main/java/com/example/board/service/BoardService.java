package com.example.board.service;

import com.example.board.common.Paging;
import com.example.board.dao.BoardDao;
import com.example.board.dto.BoardDto;
import com.example.board.dto.SearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
       log.info("totalNum="+totalNum);
       String listUrl = null;
       if(searchdto.getColname() != null) {
           listUrl = "/board?colname=" + searchdto.getColname()+"&keyword=" + searchdto.getKeyword()+"&";
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
        log.info("pageNum="+searchdto);
        return boardDao.getBoardListSearch(searchdto);
    }

    public List<BoardDto> getBoardListSearchNew(SearchDto searchdto) {
        Integer pageNum = searchdto.getPageNum();
        searchdto.setStartIndex((pageNum - 1) * searchdto.getListCnt());
        return boardDao.getBoardListSearchNew(searchdto);
    }

    public BoardDto getBoardDetail(Integer b_num) {
        return boardDao.getBoardDetail(b_num);
    }

    public boolean boardDelete(Integer b_num) {
        return boardDao.baordDelete(b_num);
    }
}
