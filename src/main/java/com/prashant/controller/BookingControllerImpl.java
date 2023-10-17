package com.prashant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<ResponseDetails> bookFlight(@RequestBody RequestObject request) {
		ResponseDetails response = service.bookFlight(request);
		return new ResponseEntity<ResponseDetails>(response, HttpStatus.CREATED);
	}

}
