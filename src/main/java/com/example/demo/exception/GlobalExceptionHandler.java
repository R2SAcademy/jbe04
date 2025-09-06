package com.example.demo.exception;

import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
  private Map<String, Object> buildBody(HttpStatus status, String message, String path) {
    return Map.of(
        "timestamp", Instant.now().toString(),
        "status", status.value(),
        "error", status.getReasonPhrase(),
        "message", message,
        "path", path
    );
  }

  /** 400 - Validation fail (RequestBody) */
  @Override
  public ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors()
        .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", Instant.now().toString());
    body.put("status", HttpStatus.BAD_REQUEST.value());
    body.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
    body.put("message", "Validation failed");
    body.put("details", errors);
    body.put("path", request.getDescription(false).replace("uri=", ""));
    return ResponseEntity.badRequest().body(body);
  }

  /** 400 - Validation fail (PathVariable / RequestParam) */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<?> handleConstraint(ConstraintViolationException ex,
      org.springframework.web.context.request.WebRequest req) {
    return ResponseEntity.badRequest().body(
        buildBody(HttpStatus.BAD_REQUEST, ex.getMessage(), req.getDescription(false).replace("uri=", ""))
    );
  }

  /** 400 - Incorrect parameter type */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<?> handleTypeMismatch(MethodArgumentTypeMismatchException ex,
      org.springframework.web.context.request.WebRequest req) {
    String msg = "Parameter '" + ex.getName() + "' must be of type " + ex.getRequiredType();
    return ResponseEntity.badRequest().body(
        buildBody(HttpStatus.BAD_REQUEST, msg, req.getDescription(false).replace("uri=", ""))
    );
  }

//  /** 401 - Authentication */
//  @ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
//  public ResponseEntity<?> handleAuth(org.springframework.security.core.AuthenticationException ex,
//      org.springframework.web.context.request.WebRequest req) {
//    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
//        buildBody(HttpStatus.UNAUTHORIZED, ex.getMessage(), req.getDescription(false).replace("uri=", ""))
//    );
//  }

  /** 403 - Authorization */
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex,
      org.springframework.web.context.request.WebRequest req) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
        buildBody(HttpStatus.FORBIDDEN, ex.getMessage(), req.getDescription(false).replace("uri=", ""))
    );
  }

  /** 404 - Resource not found (custom) */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex,
      org.springframework.web.context.request.WebRequest req) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        buildBody(HttpStatus.NOT_FOUND, ex.getMessage(), req.getDescription(false).replace("uri=", ""))
    );
  }

  /** 409 - Data conflict */
  @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
  public ResponseEntity<?> handleDataConflict(org.springframework.dao.DataIntegrityViolationException ex,
      org.springframework.web.context.request.WebRequest req) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(
        buildBody(HttpStatus.CONFLICT, "Data integrity violation", req.getDescription(false).replace("uri=", ""))
    );
  }

  /** 400 - Illegal argument */
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<?> handleIllegalArg(IllegalArgumentException ex,
      org.springframework.web.context.request.WebRequest req) {
    return ResponseEntity.badRequest().body(
        buildBody(HttpStatus.BAD_REQUEST, ex.getMessage(), req.getDescription(false).replace("uri=", ""))
    );
  }

  /** 500 - Fallback */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleGeneral(Exception ex,
      org.springframework.web.context.request.WebRequest req) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
        buildBody(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), req.getDescription(false).replace("uri=", ""))
    );
  }
}
