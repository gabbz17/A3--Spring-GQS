package com.example.demo.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class People {


    private int numberAccount;
    @NotBlank
    private String name;
    @NotBlank
    @Email(message = "Formato inválido para email!")
    private String email;
    @NotBlank
    @Size(min = 10, max = 11, message = "O número tem que ter de 10 a 11 caracteres!")
    private String number;
    @NotBlank
    @Size(min = 10, max = 11, message = "O cpf tem que conter 11 caracteres!")
    private String cpf;
    private BigDecimal balance;
}
