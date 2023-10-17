package com.prashant.model;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bookings")
@EntityListeners(AuditingEntityListener.class)
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Nonnull
	@Column(name = "flightid")
	private Integer flightId;
	@Nonnull
	@Column(name = "userid")
	private Integer userId;
	@Nonnull
	@Column(name = "numberofseats")
	private Integer numberOfSeats = 1;
	@Nonnull
	@Column(name = "totalcost")
	private Double totalCost = 0.0;
	
	public enum Status {
		INPROCESS("In Process"),
		BOOKED("Booked"),
		CANCELLED("Cancelled");
		
		String value;
		Status(String value) {
			this.value = value;
		}
	};
	@Nonnull
	@Enumerated(EnumType.STRING)
	private Status status = Status.INPROCESS;
	@Nonnull
	@Column(name = "createdat")
	private LocalDateTime createdAt;
	@Nonnull
	@Column(name = "updatedat")
	private LocalDateTime updatedAt;
}
