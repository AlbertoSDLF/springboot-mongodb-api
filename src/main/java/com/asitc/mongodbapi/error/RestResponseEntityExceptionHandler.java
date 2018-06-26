package com.asitc.mongodbapi.error;

import java.util.List;

import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ RepositoryConstraintViolationException.class })
	public ResponseEntity<List<ObjectError>> handleAccessDeniedException(final Exception ex, final WebRequest request) {
		final RepositoryConstraintViolationException nevEx = (RepositoryConstraintViolationException) ex;

		final HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);

		return new ResponseEntity<List<ObjectError>>(nevEx.getErrors().getAllErrors(), responseHeaders,
				HttpStatus.BAD_REQUEST);
	}
}
