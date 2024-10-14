package br.com.riume.backendeureka.DTOs.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeStatusRequest {
    private Long status;
    private String message;
    private String scriptId;
}
