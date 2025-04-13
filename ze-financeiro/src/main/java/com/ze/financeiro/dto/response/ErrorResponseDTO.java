package com.ze.financeiro.dto.response;

import java.time.Instant;

public record ErrorResponseDTO(
        String message,
        String path,
        int status,
        Instant timestamp
) {
}
