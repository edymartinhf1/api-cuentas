package com.bootcamp.bank.cuentas.controller;

import com.bootcamp.bank.cuentas.exception.BusinessException;
import com.bootcamp.bank.cuentas.model.ErrorBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorAdviceController {
    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ErrorBean> runtimeExceptionHandler(BusinessException ex){
        ErrorBean error = ErrorBean
                .builder()
                .codigoEstadoHttp(HttpStatus.NOT_FOUND.toString())
                .mensaje(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
