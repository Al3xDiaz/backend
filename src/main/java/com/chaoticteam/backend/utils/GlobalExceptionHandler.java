package com.chaoticteam.backend.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import io.swagger.v3.oas.annotations.Hidden;

@ControllerAdvice
@Hidden
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleRutimeException(RuntimeException ex){
        return new ResponseEntity<String>("Error en el server: " + ex.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransactionSystemException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleTransactionSystemException(TransactionSystemException ex) {
        // Aquí puedes personalizar el mensaje de error o el cuerpo de la respuesta
        return new ResponseEntity<>("Error en la transacción: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Void> handleExceptionResolver(NoResourceFoundException ex) {
        // Aquí puedes personalizar el mensaje de error o el cuerpo de la respuesta
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        String outpuString = "ErrorType: "+ex.getClass() + ", Error: " + ex.getMessage();
        System.out.println(outpuString);
        outpuString = "an error oucurred";
        return new ResponseEntity<String>(outpuString,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}