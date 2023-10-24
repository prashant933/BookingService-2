package com.prashant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingEmail {
    private String subject;
    private String body;
    private String recipientEmail;
    private Timestamp notificationTime;
}
