package com.example.tickettriage.api.dto;

import com.example.tickettriage.domain.Ticket;
import com.example.tickettriage.domain.TicketStatus;

import java.time.Instant;
import java.util.UUID;

public record TicketResponse(
        UUID id,
        String subject,
        String description,
        String customerEmail,
        TicketStatus status,
        Instant createdAt,
        Instant updatedAt) {
    public static TicketResponse from(Ticket ticket) {
        return new TicketResponse(
                ticket.getId(),
                ticket.getSubject(),
                ticket.getDescription(),
                ticket.getCustomerEmail(),
                ticket.getStatus(),
                ticket.getCreatedAt(),
                ticket.getUpdatedAt());
    }
}
