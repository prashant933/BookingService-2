package com.prashant.service;

import com.prashant.request.RequestObject;
import com.prashant.response.ResponseDetails;

public interface IBookingService {
	ResponseDetails bookFlight(RequestObject request, String authorization);
}
