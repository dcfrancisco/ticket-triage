package com.example.tickettriage.api;

import com.example.tickettriage.api.dto.CreateTicketRequest;
import com.example.tickettriage.api.dto.PagedTicketResponse;
import com.example.tickettriage.api.dto.TicketResponse;
import com.example.tickettriage.api.dto.UpdateStatusRequest;
import com.example.tickettriage.application.service.CreateTicketCommand;
import com.example.tickettriage.application.service.TicketService;
import com.example.tickettriage.domain.TicketStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
@Validated
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<TicketResponse> create(@Valid @RequestBody CreateTicketRequest request) {
        var command = new CreateTicketCommand(request.subject(), request.description(), request.customerEmail());
        var ticket = ticketService.createTicket(command);
        return ResponseEntity.created(URI.create("/api/tickets/" + ticket.getId()))
                .body(TicketResponse.from(ticket));
    }

    @GetMapping("/{id}")
    public TicketResponse get(@PathVariable UUID id) {
        return TicketResponse.from(ticketService.getTicket(id));
    }

    @GetMapping
    public PagedTicketResponse list(@RequestParam(value = "status", required = false) TicketStatus status,
            @RequestParam(value = "page", defaultValue = "0") @Min(0) int page,
            @RequestParam(value = "size", defaultValue = "20") @Min(1) @Max(100) int size) {
        return PagedTicketResponse.from(ticketService.listTickets(status, page, size));
    }

    @PostMapping("/{id}/status")
    public TicketResponse updateStatus(@PathVariable UUID id, @Valid @RequestBody UpdateStatusRequest request) {
        var ticket = ticketService.changeStatus(id, request.status(), request.allowRollback());
        return TicketResponse.from(ticket);
    }
}
