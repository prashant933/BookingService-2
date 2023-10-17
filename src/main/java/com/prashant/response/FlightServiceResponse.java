package com.prashant.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightServiceResponse {
	private Integer id;
	private String flightNumber;
	private String airplaneId;
	private String departureAirportId;
	private String arrivalAirportId;
	private LocalDateTime departureTime;
	private LocalDateTime arrivalTime;
	private Double price;
	private Integer totalSeats;
}
