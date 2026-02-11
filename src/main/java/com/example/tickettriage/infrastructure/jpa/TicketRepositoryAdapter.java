package com.example.tickettriage.infrastructure.jpa;

import com.example.tickettriage.application.port.TicketRepositoryPort;
import com.example.tickettriage.application.support.PagedResult;
import com.example.tickettriage.domain.Ticket;
import com.example.tickettriage.domain.TicketStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class TicketRepositoryAdapter implements TicketRepositoryPort {

    private final TicketJpaRepository ticketJpaRepository;

    public TicketRepositoryAdapter(TicketJpaRepository ticketJpaRepository) {
        this.ticketJpaRepository = ticketJpaRepository;
    }

    @Override
    public Ticket save(Ticket ticket) {
        TicketEntity entity = TicketEntity.fromDomain(ticket);
        TicketEntity saved = ticketJpaRepository.save(entity);
        return saved.toDomain();
    }

    @Override
    public Optional<Ticket> findById(UUID id) {
        return ticketJpaRepository.findById(id).map(TicketEntity::toDomain);
    }

    @Override
    public PagedResult<Ticket> findAll(TicketStatus statusFilter, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<TicketEntity> result = statusFilter == null
                ? ticketJpaRepository.findAll(pageable)
                : ticketJpaRepository.findByStatus(statusFilter, pageable);

        return new PagedResult<>(
                result.stream().map(TicketEntity::toDomain).toList(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages());
    }
}
