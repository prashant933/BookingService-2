package com.prashant.service;

import com.prashant.model.BookingEmail;

public interface IEmailPublisher {
    void publishMessage(BookingEmail bookingEmail);
}
