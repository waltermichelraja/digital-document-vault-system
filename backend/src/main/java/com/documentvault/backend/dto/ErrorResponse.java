package com.documentvault.backend.dto;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse{
    private LocalDateTime timestamp;
    private int status;
    private String message;
}