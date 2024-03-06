package com.quantrics.codingexamadrianbaduria.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.NoResultException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class, NoResultException.class})
    protected ResponseEntity<Object> notFoundException(Exception ex, WebRequest request) {
        var response = ErrorResponse.builder()
                .timestamp(new Date())
                .status(NOT_FOUND.value())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .message(ex.getLocalizedMessage())
                .build();

        return handleExceptionInternal(ex, response, new HttpHeaders(), NOT_FOUND, request);
    }

    @ExceptionHandler({ApplicationException.class})
    protected ResponseEntity<Object> applicationException(ApplicationException ex, WebRequest request) {
        var response = ErrorResponse.builder()
                .timestamp(new Date())
                .status(BAD_REQUEST.value())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .message(ex.getLocalizedMessage())
                .build();

        return handleExceptionInternal(ex, response, new HttpHeaders(), BAD_REQUEST, request);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> unhandledException(Exception ex, WebRequest request) {
        var response = ErrorResponse.builder()
                .timestamp(new Date())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .message("Encountered internal server error. Please contact our Support team.")
                .build();

        return handleExceptionInternal(ex, response, new HttpHeaders(), INTERNAL_SERVER_ERROR, request);
    }

    private String getMessages(BindingResult bindingResult) {
        List<String> stringList = bindingResult.getAllErrors()
                .stream()
                .map(error -> {
                    if (error instanceof FieldError) {
                        return error.getDefaultMessage();
                    }
                    return error.getDefaultMessage();
                })
                .collect(Collectors.toList());
        return stringList.stream().collect(Collectors.joining(" | ","[ "," ]"));
    }

}