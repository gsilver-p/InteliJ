package com.example.board.dao;

import com.example.board.dto.BoardDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class BoardDaoTest {
    @Autowired
    private BoardDao boardDao;

    @Test
    public void initTest(){
        assertNotNull(boardDao); //null이 아닌지 판별
    }
    @Test
    void insertDummyDataTest(){
        BoardDto boardDto = new BoardDto();
        for(int i = 1; i <= 35; i++){
            boardDto.setB_title("제목"+i).setB_contents("진짜 너무너무 어려워ㅠ_ㅠ")
                    .setB_writer("wldmsdl6276");
            boardDao.insertDummyData(boardDto);
        }
    }
    @Test
    public void findBoardListTest(){
        assertEquals(35,boardDao.getBoardListAll().size());
        boardDao.getBoardListAll().stream().forEach(boardDto -> System.out.println(boardDto));
    }
}
