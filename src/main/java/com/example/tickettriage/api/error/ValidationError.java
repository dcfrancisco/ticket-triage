package com.example.tickettriage.api.error;

public record ValidationError(String field, String message) {
}
