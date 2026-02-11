package com.example.tickettriage.api.dto;

import com.example.tickettriage.domain.TicketStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateStatusRequest(@NotNull TicketStatus status, boolean allowRollback) {
}
