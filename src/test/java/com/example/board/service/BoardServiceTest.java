package com.example.board.service;

import com.example.board.dto.BoardDto;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class BoardServiceTest {
    private static Logger log = LoggerFactory.getLogger(BoardServiceTest.class);
    @Autowired
    private BoardService boardService;

    @DisplayName("종속 주입(DI) 테스트")
    @Test
    public void testDI() {
        log.info("boardService:{}", boardService);
        Assertions.assertNotEquals(null,boardService);
    }

    @Test
    public void getBoardList() {
        List<BoardDto> boardList = boardService.getBoardList(1);
        boardList.forEach(b->log.info("b:{}",b));
        Assertions.assertEquals(10,boardList.size());
    }
}
