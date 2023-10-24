package com.prashant.controller;

import org.springframework.http.ResponseEntity;

import com.prashant.request.AuthServiceRequestObject;
import com.prashant.response.AuthServiceResponse;
import com.prashant.response.ResponseDetails;

public interface IAuthController {
	ResponseEntity<ResponseDetails> signup(AuthServiceRequestObject request);
	ResponseEntity<AuthServiceResponse> signin(AuthServiceRequestObject request);
}
