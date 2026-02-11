package com.example.tickettriage.api.error;

import java.net.URI;
import java.util.List;

public record ProblemResponse(
        URI type,
        String title,
        int status,
        String detail,
        URI instance,
        List<ValidationError> errors) {
    public static ProblemResponse of(URI type, String title, int status, String detail, URI instance) {
        return new ProblemResponse(type, title, status, detail, instance, List.of());
    }
}
