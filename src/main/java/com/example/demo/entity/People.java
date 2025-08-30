package com.example.demo.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class People {


    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    @Email(message = "Formato inválido para email!")
    private String email;
    @NotBlank
    @Size(min = 10, max = 11, message = "O número tem que ter de 10 a 11 caracteres!")
    private String number;
}
