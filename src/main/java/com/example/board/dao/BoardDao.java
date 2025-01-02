package com.example.board.dao;

import com.example.board.dto.BoardDto;
import com.example.board.dto.SearchDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface BoardDao {

    void insertDummyData(BoardDto boardDto);

    ArrayList<BoardDto> getBoardList(Map<String, Integer> pageMap);

    @Select("select * from board")
    List<BoardDto> getBoardListAll();

     int getBoardCount(SearchDto searchdto);
    // 동적쿼리 이용해서 키워드가 있거나 없어도 처리

    List<BoardDto> getBoardListSearch(SearchDto searchDto);

    List<BoardDto> getBoardListSearchNew(SearchDto searchDto);
}
