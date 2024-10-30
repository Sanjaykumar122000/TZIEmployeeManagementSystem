package DepartmentService.DepartmentService.Exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DepartmentNotFoundException.class)

	public ResponseEntity<Object> handleDepartmentNotFound(DepartmentNotFoundException ex, WebRequest request) {
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", HttpStatus.NOT_FOUND.value());
		body.put("error", "Department Not Found");
		body.put("message", ex.getMessage());
		body.put("path", request.getDescription(false));

		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		body.put("error", "Internal Server Error");
		body.put("message", ex.getMessage());
		body.put("path", request.getDescription(false));

		return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}