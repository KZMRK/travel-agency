package com.kazmiruk.travel_agency.dto;

import java.time.LocalDateTime;

public record ErrorDto(LocalDateTime timestamp, int status, String message) {
}
