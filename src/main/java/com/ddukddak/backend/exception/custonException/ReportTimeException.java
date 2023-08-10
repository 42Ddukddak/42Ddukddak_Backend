package com.ddukddak.backend.exception.custonException;

public class ReportTimeException extends RuntimeException {
    private int errorCode;

    public ReportTimeException() {
        super("신고한지 3분도 채 안됌;; 신중하자");
        this.errorCode = 400;
    }
}
