package com.okomski.loanservice.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class ErrorResponse implements RestResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private String status;
    private LocalDateTime timestamp;
    private final List<String> errors;
    private final String path;


    public ErrorResponse(List<String> errors, String path) {
        this.timestamp = LocalDateTime.now();
        this.errors = errors;
        this.path = path;
    }
}
