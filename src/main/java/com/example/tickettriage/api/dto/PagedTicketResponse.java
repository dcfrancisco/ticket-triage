package com.example.tickettriage.api.dto;

import com.example.tickettriage.application.support.PagedResult;
import com.example.tickettriage.domain.Ticket;

import java.util.List;

public record PagedTicketResponse(List<TicketResponse> content, int page, int size, long totalElements,
        int totalPages) {
    public static PagedTicketResponse from(PagedResult<Ticket> result) {
        List<TicketResponse> tickets = result.content().stream().map(TicketResponse::from).toList();
        return new PagedTicketResponse(tickets, result.page(), result.size(), result.totalElements(),
                result.totalPages());
    }
}
