package com.example.board.advice;

import com.example.board.exception.IdCheckException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdviceRest {
    // 아이디 중복체크 예외, 댓글 추가 예외
    @ExceptionHandler(IdCheckException.class)
    public ResponseEntity<String> idCheckHandler(IdCheckException e) {
        log.info(">>>>IdCheckException Advice");
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_GATEWAY);
    }
}
