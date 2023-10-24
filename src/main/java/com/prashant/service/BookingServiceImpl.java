package com.prashant.service;

import java.time.LocalDateTime;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.prashant.dao.IBookingDao;
import com.prashant.exception.BadRequestException;
import com.prashant.exception.NotFoundException;
import com.prashant.model.Booking;
import com.prashant.model.Booking.Status;
import com.prashant.request.RequestObject;
import com.prashant.response.FlightServiceResponse;
import com.prashant.response.ResponseDetails;

@Service
public class BookingServiceImpl implements IBookingService {
	
	@Autowired
	private IBookingDao dao;

	@Autowired
	private IAuthService authService;

	private final String FLIGHT_SERVICE_REQUEST_URL = "http://localhost:3001/flightService/api/flights/";

	@Override
	public ResponseDetails bookFlight(RequestObject request, String authorization) {
		validateInputs(request);
		Integer userId = validateAuthHeader(authorization);
		
		Integer flightId = request.getFlightId();
		Integer totalSeats = request.getTotalSeats();
		
		FlightServiceResponse flightServiceResponse = getFlight(flightId);
		
		if(flightServiceResponse == null) {
			throw new NotFoundException(String.format("Flight with given ID: %d does not exists", flightId));
		}
		
		Double totalCost = flightServiceResponse.getPrice() * totalSeats;
		
		Booking booking = new Booking();
		booking.setFlightId(flightId);
		booking.setNumberOfSeats(totalSeats);
		booking.setUserId(userId);
		booking.setTotalCost(totalCost);
		booking.setStatus(Status.BOOKED);
		booking.setCreatedAt(LocalDateTime.now());
		booking.setUpdatedAt(LocalDateTime.now());
		
		//Decrease the number of seats for the current flight in flights table
		String seatsUpdationUrl = "http://localhost:3001/flightService/api/flights/updateSeats/" + flightId + 
				"?decrement=" +totalSeats;
		WebClient webClient = WebClient.create();
		webClient
		.patch()
		.uri(seatsUpdationUrl)
		.retrieve()
		.bodyToMono(ResponseDetails.class)
		.block();
		
		dao.save(booking);
		
		ResponseDetails response = new ResponseDetails();
		response.setMessage("Flight booked successfully. User id: "+userId +" Total Cost is: "+totalCost);
		response.setSuccess(true);
		return response;
	}
	
	private void validateInputs(RequestObject request) {
		Integer flightId = request.getFlightId();
		Integer totalSeats = request.getTotalSeats();
		
		if(flightId == null)
			throw new NotFoundException("Flight ID cannot be null");
		if(totalSeats == null)
			throw new NotFoundException("Total number of seats cannot be null");
		if(totalSeats <=0 )
			throw new BadRequestException("Total number of seats should be positive");
	}
	
	private FlightServiceResponse getFlight(Integer flightId) {
		final String flightServiceUrl = FLIGHT_SERVICE_REQUEST_URL + flightId.toString();
		
		WebClient webClient = WebClient.create();
		
		FlightServiceResponse flightServiceResponse = webClient
		.get()
		.uri(flightServiceUrl)
		.accept(MediaType.APPLICATION_JSON)
		.retrieve()
		.bodyToMono(FlightServiceResponse.class)
		.block();
		
		return flightServiceResponse;
	}

	private Integer validateAuthHeader(String authorization) {
		if(!authorization.startsWith("Bearer: ")) {
			throw new BadRequestException("Invalid Authorization header provided");
		}
		String token = authorization.substring(8);
		if(token.length() == 0) {
			throw new BadRequestException("Please provide valid token in header");
		}
		Jws<Claims> resp = authService.validateToken(token);
		Integer userId = resp.getBody().get("id", Integer.class);
		return userId;
	}
}
