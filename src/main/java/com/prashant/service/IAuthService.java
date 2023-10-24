package com.prashant.service;

import com.prashant.request.AuthServiceRequestObject;
import com.prashant.response.AuthServiceResponse;
import com.prashant.response.ResponseDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public interface IAuthService {
	ResponseDetails signup(AuthServiceRequestObject request);
	AuthServiceResponse signin(AuthServiceRequestObject request);
	Jws<Claims> validateToken(String token);
}
