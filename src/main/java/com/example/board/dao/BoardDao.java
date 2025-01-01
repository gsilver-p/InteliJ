package com.example.board.dao;

import com.example.board.dto.BoardDto;
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
    List<BoardDto> getBoardListAll(Map<String, Integer> pageMap);

    @Select("select count(*) from board")
    int getBoardCount();

}
