package com.example.board.dao;

import com.example.board.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardDao {

    void insertDummyData(BoardDto boardDto);
}
