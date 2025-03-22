package clinic.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<Void> handleError404() {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<ValidationErrorData>> handleError400(
      MethodArgumentNotValidException ex) {
    var errorList = ex.getFieldErrors();

    return ResponseEntity.badRequest()
        .body(errorList.stream().map(ValidationErrorData::new).toList());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<String> handleError400(HttpMessageNotReadableException ex) {
    return ResponseEntity.badRequest().body(ex.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleError500(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Error: " + ex.getLocalizedMessage());
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<String> handleBadCredentialsError() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials.");
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<String> handleAuthenticationError() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication had failed.");
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<String> handleAccessDeniedError() {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied.");
  }

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<String> handleValidationError(ValidationException ex) {
    return ResponseEntity.badRequest().body(ex.getMessage());
  }
}
