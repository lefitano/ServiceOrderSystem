package com.barberFlow.barberFlow.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Validated
@ConfigurationProperties(prefix = "barberflow.jwt")
public record JwtProperties(@NotBlank @Size(min = 32) String secret, @Min(1) long expirationMinutes) {
}
