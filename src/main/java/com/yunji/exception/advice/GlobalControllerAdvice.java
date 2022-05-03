package com.yunji.exception.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalControllerAdvice {


    @ExceptionHandler(value = Exception.class)
    public ResponseEntity exception(Exception e){

        System.out.println(e.getClass().getName());

        System.out.println("--------------------");
        System.out.println(e.getLocalizedMessage());
        System.out.println("--------------------");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
    }

    //이런 식으로 글로벌한 예외 설정 가능
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity MethodArgumentNotValidException(MethodArgumentNotValidException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
