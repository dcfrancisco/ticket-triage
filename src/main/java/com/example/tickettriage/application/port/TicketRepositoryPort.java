package com.example.tickettriage.application.port;

import com.example.tickettriage.domain.Ticket;
import com.example.tickettriage.domain.TicketStatus;
import com.example.tickettriage.application.support.PagedResult;

import java.util.Optional;
import java.util.UUID;

public interface TicketRepositoryPort {
    Ticket save(Ticket ticket);

    Optional<Ticket> findById(UUID id);

    PagedResult<Ticket> findAll(TicketStatus statusFilter, int page, int size);
}
