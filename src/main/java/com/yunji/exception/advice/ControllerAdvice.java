package com.yunji.exception.advice;

import com.yunji.exception.controller.ApiController;
import com.yunji.exception.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import com.yunji.exception.dto.Error;


@RestControllerAdvice(basePackageClasses = ApiController.class)
public class ControllerAdvice {


    @ExceptionHandler(value = Exception.class)
    public ResponseEntity exception(Exception e){

        System.out.println(e.getClass().getName());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
    }

    //이런 식으로 글로벌한 예외 설정 가능
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity MethodArgumentNotValidException(MethodArgumentNotValidException e){





        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }


    //이런 식으로 글로벌한 예외 설정 가능
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity ConstraintViolationException(ConstraintViolationException e, HttpServletRequest httpServletRequest){
        List<Error> errorList = new ArrayList<>();
        e.getConstraintViolations().forEach(error -> {
            String message = error.getMessage();
            String invalidValue = error.getInvalidValue().toString();


            Error errorMessage = new Error();


            errorMessage.setMessage(message);
            errorMessage.setInvalidValue(invalidValue);
            errorMessage.setField(e.getClass().getName());

            errorList.add(errorMessage);

        });

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorList(errorList);
        errorResponse.setMessage("");
        errorResponse.setRequestUrl(httpServletRequest.getRequestURI());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.name());
        errorResponse.setResultCode("FAIL");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    //이런 식으로 글로벌한 예외 설정 가능
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity MissingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest httpServletRequest){
        List<Error> errorList = new ArrayList<>();
        String fieldName = e.getParameterName();

        Error errorMessage = new Error();

        errorMessage.setMessage(e.getMessage());
        errorMessage.setField(fieldName);

        errorList.add(errorMessage);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorList(errorList);
        errorResponse.setMessage("");
        errorResponse.setRequestUrl(httpServletRequest.getRequestURI());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.name());
        errorResponse.setResultCode("FAIL");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }


}
