package com.prashant.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

import com.prashant.exception.BadRequestException;
import com.prashant.exception.NotFoundException;
import com.prashant.exception.SeatsNotAvailableException;
import com.prashant.response.ResponseDetails;

@RestControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
	
	@org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ResponseDetails> handleNotFoundException(NotFoundException e) {
		ResponseDetails response = new ResponseDetails(e.getMessage(), false);
		return new ResponseEntity<ResponseDetails>(response, HttpStatus.BAD_REQUEST);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(SeatsNotAvailableException.class)
	public ResponseEntity<ResponseDetails> handleSeatsNotAvailableException(SeatsNotAvailableException e) {
		ResponseDetails response = new ResponseDetails(e.getMessage(), false);
		return new ResponseEntity<ResponseDetails>(response, HttpStatus.BAD_REQUEST);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ResponseDetails> handleBadRequestException(BadRequestException e) {
		ResponseDetails response = new ResponseDetails(e.getMessage(), false);
		return new ResponseEntity<ResponseDetails>(response, HttpStatus.BAD_REQUEST);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(WebClientResponseException.NotFound.class)
	public ResponseEntity<ResponseDetails> handleNotFoundExceptionFromFlightService() {
		ResponseDetails response = new ResponseDetails("Flight with given ID does not exists", false);
		return new ResponseEntity<ResponseDetails>(response, HttpStatus.BAD_REQUEST);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(WebClientResponseException.BadRequest.class)
	public ResponseEntity<ResponseDetails> handleBadRequestExceptionFromFlightService() {
		ResponseDetails response = new ResponseDetails("Flight does not have required number of available seats", false);
		return new ResponseEntity<ResponseDetails>(response, HttpStatus.BAD_REQUEST);
	}
	@org.springframework.web.bind.annotation.ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ResponseDetails> handleUserNameNotFoundException(UsernameNotFoundException e) {
		ResponseDetails response = new ResponseDetails();
		response.setMessage(e.getMessage());
		response.setSuccess(false);
		return new ResponseEntity<ResponseDetails>(response, HttpStatus.UNAUTHORIZED);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(MissingRequestHeaderException.class)
	public ResponseEntity<ResponseDetails> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
		ResponseDetails response = new ResponseDetails();
		response.setMessage("Please provide Authorization header in the request");
		response.setSuccess(false);
		return new ResponseEntity<ResponseDetails>(response, HttpStatus.UNAUTHORIZED);
	}
}
