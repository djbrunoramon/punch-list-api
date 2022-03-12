package br.com.engbr.examples.punchlistapi.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ResponseError {

    private HttpStatus status = HttpStatus.BAD_REQUEST;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    private String message;

    public ResponseError() {
        timestamp = LocalDateTime.now();
    }

    public ResponseError(HttpStatus status) {
        this();
        this.status = status;
    }

    public ResponseError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error: " + ex.getMessage();
    }

    public ResponseError(HttpStatus status, String message) {
        this();
        this.status = status;
        this.message = message;
    }

    public ResponseError(String message) {
        this();
        this.message = message;
    }
}
