package com.ten022.diary.exception.advice;

import com.ten022.diary.exception.NotFoundException;
import com.ten022.diary.exception.NotValidatedValueException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResult> notFoundExceptionHandler(NotFoundException e){
        logError(e);
        ErrorResult errorResult = new ErrorResult("NotFoundException", e.getMessage());
        return new ResponseEntity(errorResult, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> illegalAccessExceptionHandler(IllegalAccessException e){
        logError(e);
        ErrorResult errorResult = new ErrorResult("IllegalAccessException", "유효하지 않은 토큰 값으로 접근할 수 없습니다.");
        return new ResponseEntity(errorResult, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> ExceptionHandler(NotValidatedValueException e){
        logError(e);
        ErrorResult errorResult = new ErrorResult("NotValidatedValueException", "request 값의 형태가 올바르지 않습니다.");
        return new ResponseEntity(errorResult, HttpStatus.BAD_REQUEST);
    }

    public void logError(Exception e){
        log.error("[exceptionHandler] ", e);
    }

}