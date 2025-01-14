package com.example.board.exception;

public class CommonException extends RuntimeException {
    public CommonException(String message) {
        super("공통 예외 처리 입니다.");
    }
}
