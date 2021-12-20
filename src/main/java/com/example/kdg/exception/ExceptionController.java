package com.example.kdg.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ExceptionController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     **** Controller에서 @valid 어노테이션을 사용하여
     **** 유효성 체크에 통과하지 못하면 유효성을 통과하지 못한 필드를 바인딩하여 반환하는 하는 역할
     **** 해당 예외처리가 없으면 아래와 같이 예외 발생
     **** Resolved [org.springframework.validation.BindException: org.springframework.validation.BeanPropertyBindingResult: 1 errors
     **/
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleBindException(BindException ex) {
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        List errorList = new ArrayList<>();

        for (FieldError fieldError : errors) {
            Map err = new HashMap();
            err.put("fieldName", fieldError.getField());
            err.put("message", fieldError.getDefaultMessage());
            errorList.add(err);
        }
        ErrorResponse errorResponse = new ErrorResponse(400, errorList);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     **** Servic 단에서 SQL에 예외가 발생했을 때
     **/
//    @ExceptionHandler({SQLException.class, DataAccessException.class})
//    @ResponseBody
//    public ResponseEntity<ErrorResponse> handleSQLException(Exception e){
//        ErrorResponse errorResponse = new ErrorResponse(500, "DB 접속 오류가 발생했습니다. DB정보를 다시 확인해주세요.");
//        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
