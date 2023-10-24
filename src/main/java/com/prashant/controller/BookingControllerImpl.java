package com.prashant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.prashant.request.RequestObject;
import com.prashant.response.ResponseDetails;
import com.prashant.service.IBookingService;

@RestController
@RequestMapping("/api/bookingService")
public class BookingControllerImpl implements IBookingController {
	
	@Autowired
	private IBookingService service;

	@Override
	@PostMapping("/book")
	public ResponseEntity<ResponseDetails> bookFlight(@RequestBody RequestObject request,
													  @RequestHeader("Authorization") String authorization) {
		ResponseDetails response = service.bookFlight(request, authorization);
		return new ResponseEntity<ResponseDetails>(response, HttpStatus.CREATED);
	}

}
