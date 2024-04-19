package com.kazmiruk.travel_agency.model.dto;

import java.time.LocalDateTime;

public record ErrorDto<T>(LocalDateTime timestamp, int status, T message) {
}
