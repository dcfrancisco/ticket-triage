package com.example.tickettriage.domain;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Ticket {

    private final UUID id;
    private String subject;
    private String description;
    private String customerEmail;
    private TicketStatus status;
    private Instant createdAt;
    private Instant updatedAt;

    private Ticket(UUID id,
            String subject,
            String description,
            String customerEmail,
            TicketStatus status,
            Instant createdAt,
            Instant updatedAt) {
        this.id = Objects.requireNonNull(id);
        this.subject = Objects.requireNonNull(subject);
        this.description = Objects.requireNonNull(description);
        this.customerEmail = Objects.requireNonNull(customerEmail);
        this.status = Objects.requireNonNull(status);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);
    }

    public static Ticket createNew(UUID id, String subject, String description, String customerEmail, Instant now) {
        return new Ticket(id, subject, description, customerEmail, TicketStatus.NEW, now, now);
    }

    public static Ticket restore(UUID id,
            String subject,
            String description,
            String customerEmail,
            TicketStatus status,
            Instant createdAt,
            Instant updatedAt) {
        return new Ticket(id, subject, description, customerEmail, status, createdAt, updatedAt);
    }

    public void transitionTo(TicketStatus newStatus, boolean allowRollback) {
        if (!canTransitionTo(newStatus, allowRollback)) {
            throw new IllegalStateException("Illegal status transition from " + status + " to " + newStatus);
        }
        this.status = newStatus;
        this.updatedAt = Instant.now();
    }

    public boolean canTransitionTo(TicketStatus newStatus, boolean allowRollback) {
        if (newStatus == status) {
            return true;
        }
        return switch (status) {
            case NEW -> newStatus == TicketStatus.TRIAGED;
            case TRIAGED -> newStatus == TicketStatus.IN_PROGRESS;
            case IN_PROGRESS -> newStatus == TicketStatus.DONE || (allowRollback && newStatus == TicketStatus.TRIAGED);
            case DONE -> false;
        };
    }

    public UUID getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
