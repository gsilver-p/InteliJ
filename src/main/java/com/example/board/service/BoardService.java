package com.example.board.service;

import com.example.board.common.Paging;
import com.example.board.dao.BoardDao;
import com.example.board.dto.BoardDto;
import com.example.board.dto.SearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardService {
    public static final Integer listcnt = 10;
    private final BoardDao boardDao;

    public List<BoardDto> getBoardList(Integer pageNum) {
        // select * from board order b_date desc limit 0,10  , 1page
        // select * from board order b_date desc limit 10,10  , 2page
        // select * from board order b_date desc limit 20,10  , 3page
        Map<String, Integer> pageMap = new HashMap<>();
        pageMap.put("startIndex", (pageNum - 1) * 10);
        pageMap.put("pageSize", 10);  // listCnt와 같은 의미
        return boardDao.getBoardList(pageMap);
    }

    public String getPaging(Integer pageNum) {
        int totalNum = boardDao.getBoardCount();
        Paging paging = new Paging(totalNum, pageNum, 10, 2, "/board?");
        return paging.makeHtmlPaging();
    }

    // 검색을 통한 게시글 리스트
    public List<BoardDto> getBoardList(SearchDto searchDto) {
        Integer pageNum = searchDto.getPageNum();
        searchDto.setStartIndex((pageNum - 1) * BoardService.listcnt);
        return boardDao.getBoardListSearch(searchDto);  // 검색결과가 없거나 실패시 null 반환
    }
}
