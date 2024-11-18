package com.lobo.onThisDay.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.DateTimeException;

/**
 *
 * Classe que fará o tratamento de erros da API
 *
 * @author Gustavo Lobo
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<String> handleDateTimeException(DateTimeException e) {
        return ResponseEntity.badRequest().body("A data informada não está num formato suportado: " + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception e) {
        LOGGER.error("Ocorreu um erro durante a requisição: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().body("Ocorreu um erro durante a requisição.");
    }

}
