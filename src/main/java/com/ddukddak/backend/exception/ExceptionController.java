package com.ddukddak.backend.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity handleReportTimeException(RuntimeException e) {
        log.info("-----------------[ReportTimeException]-----------------");
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
