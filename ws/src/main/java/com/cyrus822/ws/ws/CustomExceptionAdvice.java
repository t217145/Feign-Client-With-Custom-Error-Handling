package com.cyrus822.ws.ws;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, String>> handleBusinessError(CustomException err){
        Map<String, String> rtn = Map.of("errCode", err.getErrCode(), "errMsg", err.getErrMsg());
        return ResponseEntity.badRequest().body(rtn);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralError(Exception err){
        Map<String, String> rtn = Map.of("errCode", "General", "errMsg", err.getMessage());
        return ResponseEntity.badRequest().body(rtn);
    }
}
