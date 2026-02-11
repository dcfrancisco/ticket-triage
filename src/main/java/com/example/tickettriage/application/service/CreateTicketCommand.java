package com.example.tickettriage.application.service;

public record CreateTicketCommand(String subject, String description, String customerEmail) {
}
