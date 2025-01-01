package com.example.board.dao;

import com.example.board.dto.BoardDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class BoardDaoTest {
    @Autowired  // 필드 주입만 되는거야!
    private BoardDao boardDao;

    @Test
    void insertDummyDataTest() {
        BoardDto boardDto = new BoardDto();
        for (int i = 0; i < 35; i++) {
            boardDto.setB_title("제목" + i).setB_contents("진짜 너무너무 어려워").setB_id("wldmsdl6276");
            boardDao.insertDummyData(boardDto);
        }
    }

    @Test
    public void findBoardListTest() {
        assertEquals(35,boardDao.getBoardListAll().size());
        boardDao.getBoardListAll().stream().forEach(boardDto -> System.out.println(boardDto));
    }
}
