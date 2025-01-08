package com.example.board.controller;

import com.example.board.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardRestController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/reply")
    public String reply() {
        return null;
    }


}
