package com.prashant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prashant.request.AuthServiceRequestObject;
import com.prashant.response.AuthServiceResponse;
import com.prashant.response.ResponseDetails;
import com.prashant.service.IAuthService;

@RestController
@RequestMapping("/api")
public class AuthControllerImpl implements IAuthController {
	
	@Autowired
	private IAuthService service;

	@PostMapping("/signup")
	public ResponseEntity<ResponseDetails> signup(@RequestBody AuthServiceRequestObject request) {
		ResponseDetails response = service.signup(request);
		return new ResponseEntity<ResponseDetails>(response, HttpStatus.CREATED);
	}
	@PostMapping("/signin")
	public ResponseEntity<AuthServiceResponse> signin(@RequestBody AuthServiceRequestObject request) {
		AuthServiceResponse response = service.signin(request);
		return new ResponseEntity<AuthServiceResponse>(response, HttpStatus.OK);
	}
}
