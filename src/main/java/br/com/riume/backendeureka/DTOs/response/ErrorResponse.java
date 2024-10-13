package br.com.riume.backendeureka.DTOs.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class ErrorResponse {
    private HttpStatus httpStatus;
    private String message;

    public ErrorResponse(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
