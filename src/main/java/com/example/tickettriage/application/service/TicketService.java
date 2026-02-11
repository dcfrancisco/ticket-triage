package com.example.tickettriage.application.service;

import com.example.tickettriage.application.port.TicketRepositoryPort;
import com.example.tickettriage.application.support.PagedResult;
import com.example.tickettriage.domain.Ticket;
import com.example.tickettriage.domain.TicketStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class TicketService {

    private final TicketRepositoryPort ticketRepository;

    public TicketService(TicketRepositoryPort ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket createTicket(CreateTicketCommand command) {
        Instant now = Instant.now();
        Ticket ticket = Ticket.createNew(UUID.randomUUID(), command.subject(), command.description(),
                command.customerEmail(), now);
        return ticketRepository.save(ticket);
    }

    public Ticket getTicket(UUID id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket not found: " + id));
    }

    public PagedResult<Ticket> listTickets(TicketStatus status, int page, int size) {
        return ticketRepository.findAll(status, page, size);
    }

    public Ticket changeStatus(UUID id, TicketStatus newStatus, boolean allowRollback) {
        Ticket ticket = getTicket(id);
        ticket.transitionTo(newStatus, allowRollback);
        return ticketRepository.save(ticket);
    }
}
