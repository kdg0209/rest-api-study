package com.example.kdg.exception;

import com.example.kdg.common.CurrentDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ExceptionController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private CurrentDateTime currentDateTime;

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
     **** Servic 단에서 SQ에 예외가 발생했을 때
     **/
    @ExceptionHandler({SQLException.class, DataAccessException.class})
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleSQLException(Exception e){
        ErrorResponse errorResponse = new ErrorResponse(500, "DB 접속 오류가 발생했습니다. DB정보를 다시 확인해주세요.");

        logger.error(currentDateTime.getCurrentDateTime() + " ===> " +  e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    create table vsa.t_account
//            (
//    uid           int auto_increment
//    primary key,
//    user_id       varchar(70)                not null,
//    user_pwd      varchar(400)               not null,
//    role_code     varchar(4)      default '' not null,
//    register_date datetime                   not null,
//    register_id   int                        not null,
//    update_date   datetime                   null,
//    update_id     int                        null,
//    user_status   int(1) unsigned default 0  null,
//    pwd_validate  datetime                   null
//            )
//    comment '사용자 정보 ' collate = utf8mb4_unicode_ci;
//
//    create index user_id
//    on vsa.t_account (user_id);


//    create table vsa.t_account_info
//            (
//    uid                 int                    not null
//    primary key,
//    user_id             varchar(70)            not null,
//    user_name           varchar(20) default '' null,
//    user_addr           varchar(100)           null,
//    user_addr2          varchar(100)           null,
//    user_tel            varchar(15) default '' not null,
//    user_gender         int(1)                 null,
//    user_birth_day      date                   null,
//    user_channel        int(1)      default 1  null,
//    user_photo_url      text                   null comment '사용자 사진 주소',
//    user_memo           text                   null comment '메모',
//    register_store_code varchar(50)            null,
//    update_date         datetime               null,
//    update_id           int                    null,
//    visitor_count       int                    null,
//    login_time          datetime               null
//            )
//    comment '사용자 상세' collate = utf8mb4_unicode_ci;
//
//    create index user_id
//    on vsa.t_account_info (user_id);


}
