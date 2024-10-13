package br.com.riume.backendeureka.DTOs.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterClientRequest {

    private String name;

    private String email;

    private String phone;

}
