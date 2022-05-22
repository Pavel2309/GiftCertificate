package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorDetails handleResourceNotFoundException(ResourceNotFoundException e) {
        List<String> details = new ArrayList<>();
        details.add(String.format("a resource with the id %s not found", e.getMessage()));
        return ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .errorCode("404" + e.getMessage())
                .message("Resource not found")
                .errors(details)
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDetails handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        List<String> details = new ArrayList<>();
        details.add(e.getMessage());
        return ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .errorCode("40001")
                .message("Malformed JSON request")
                .errors(details)
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDetails handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        List<String> details = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getObjectName() + " : " + error.getDefaultMessage())
                .toList();
        return ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .errorCode("40002")
                .message("Validation errors")
                .errors(details)
                .build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDetails handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        List<String> details = new ArrayList<>();
        details.add(e.getMessage());
        return ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .errorCode("40003")
                .message("Type mismatch")
                .errors(details)
                .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDetails handleConstraintViolationException(ConstraintViolationException e) {
        List<String> details = new ArrayList<>();
        details.add(e.getMessage());
        return ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .errorCode("40004")
                .message("Constraint violations")
                .errors(details)
                .build();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDetails handleMissingServletRequestParameter(MissingServletRequestParameterException e) {
        List<String> details = new ArrayList<>();
        details.add(e.getParameterName() + " parameter is missing");
        return ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .errorCode("40005")
                .message("Missing parameters")
                .errors(details)
                .build();
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    ErrorDetails handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException e) {
        List<String> details = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        builder.append(e.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        e.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        details.add(builder.toString());
        return ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .errorCode("40006")
                .message("Unsupported media type")
                .errors(details)
                .build();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorDetails handleNoHandlerFoundException(NoHandlerFoundException e) {
        List<String> details = new ArrayList<>();
        details.add(String.format("Could not find the %s method for URL %s",
                e.getHttpMethod(), e.getRequestURL()));
        return ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .errorCode("40007")
                .message("Method not found")
                .errors(details)
                .build();
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDetails handleResourceAlreadyExistException(ResourceAlreadyExistException e) {
        List<String> details = new ArrayList<>();
        details.add(String.format("Resource with with the %s already exist", e.getMessage()));
        return ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .errorCode("40007")
                .message("Resource already exist")
                .errors(details)
                .build();

    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDetails internalError(Exception e) {
        List<String> details = new ArrayList<>();
        details.add(e.getLocalizedMessage());
        return ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .errorCode("50001")
                .message("Unexpected error occurred")
                .errors(details)
                .build();
    }
}
