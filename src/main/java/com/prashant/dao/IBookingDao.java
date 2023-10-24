package com.prashant.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prashant.model.Booking;

public interface IBookingDao extends JpaRepository<Booking, Integer> {
}
