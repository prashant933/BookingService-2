package com.prashant.controller;

import org.springframework.http.ResponseEntity;

import com.prashant.request.RequestObject;
import com.prashant.response.ResponseDetails;

public interface IBookingController {
	ResponseEntity<ResponseDetails> bookFlight(RequestObject request, String authorization);
}
