package com.example.tickettriage.infrastructure.jpa;

import com.example.tickettriage.domain.TicketStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketJpaRepository extends JpaRepository<TicketEntity, UUID> {
    Page<TicketEntity> findByStatus(TicketStatus status, Pageable pageable);
}
