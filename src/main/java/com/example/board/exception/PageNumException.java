package com.example.board.exception;

// 예외 클래스 정의
public class PageNumException extends RuntimeException {
    public PageNumException(String msg) {
        super(msg);  // getMSG하면 나와!
    }
}
