package com.inacal.system.management.config;

import java.time.LocalDateTime;
import com.inacal.management.exception.*;
import org.springframework.http.HttpStatus;
import com.inacal.management.http.ErrorType;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.validation.ObjectError;
import com.inacal.management.http.HttpErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;

@ControllerAdvice
public class ExceptionConfiguration {
    public HttpErrorResponse buildResponse(HttpServletRequest request, AbstractException exception, String message) {
        String method = request.getMethod();
        String path = request.getRequestURL().toString();

        return new HttpErrorResponse(
                exception.errorType,
                message,
                method + " " + path,
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<HttpErrorResponse> handleNotFound(NotFoundException exception, HttpServletRequest request) {
        HttpErrorResponse apiResponse = buildResponse(request, exception, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<HttpErrorResponse> handleUnauthorized(UnauthorizedException exception, HttpServletRequest request) {
        HttpErrorResponse apiResponse = buildResponse(request, exception, exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<HttpErrorResponse> handleForbidden(ForbiddenException exception, HttpServletRequest request) {
        HttpErrorResponse apiResponse = buildResponse(request, exception, exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<HttpErrorResponse> handleBadRequest(BadRequestException exception, HttpServletRequest request) {
        HttpErrorResponse apiResponse = buildResponse(request, exception, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpErrorResponse> handleValidationExceptions(MethodArgumentNotValidException exception, HttpServletRequest request) {
        ObjectError error = exception.getBindingResult().getAllErrors().stream().findFirst().orElseThrow();
        BadRequestException newException = new BadRequestException(ErrorType.BAD_REQUEST_PAYLOAD, error.getDefaultMessage());
        HttpErrorResponse apiResponse = buildResponse(request, newException, error.getDefaultMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HttpErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException exception, HttpServletRequest request) {
        BadRequestException newException = new BadRequestException(ErrorType.BAD_REQUEST_PAYLOAD, exception.getMessage());
        HttpErrorResponse apiResponse = buildResponse(request, newException, "The request body must not be null");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<HttpErrorResponse> handleInternalError(InternalServerException exception, HttpServletRequest request) {
        exception.printStackTrace(System.err);
        HttpErrorResponse apiResponse = buildResponse(request, exception, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpErrorResponse> handleException(Exception exception, HttpServletRequest request) {
        exception.printStackTrace(System.err);
        InternalServerException newException = new InternalServerException(exception);
        HttpErrorResponse apiResponse = buildResponse(request, newException, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }
}
