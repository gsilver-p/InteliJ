package com.example.board.exception;

public class DBException extends RuntimeException {
    public DBException() {
        super("@Transactional은 기본적으로 RuntimeException or Error 예외 발생하면 rollback");
    }
}
