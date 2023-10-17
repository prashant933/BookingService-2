package com.prashant.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

import com.prashant.exception.BadRequestException;
import com.prashant.exception.NotFoundException;
import com.prashant.response.ResponseDetails;

@RestControllerAdvice
public class BookingExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ResponseDetails> handleNotFoundException(NotFoundException e) {
		ResponseDetails response = new ResponseDetails(e.getMessage(), false);
		return new ResponseEntity<ResponseDetails>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ResponseDetails> handleBadRequestException(BadRequestException e) {
		ResponseDetails response = new ResponseDetails(e.getMessage(), false);
		return new ResponseEntity<ResponseDetails>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(WebClientResponseException.NotFound.class)
	public ResponseEntity<ResponseDetails> handleNotFoundExceptionFromFlightService() {
		ResponseDetails response = new ResponseDetails("Flight with given ID does not exists", false);
		return new ResponseEntity<ResponseDetails>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(WebClientResponseException.BadRequest.class)
	public ResponseEntity<ResponseDetails> handleBadRequestExceptionFromFlightService() {
		ResponseDetails response = new ResponseDetails("Flight does not have required number of available seats", false);
		return new ResponseEntity<ResponseDetails>(response, HttpStatus.BAD_REQUEST);
	}
}
