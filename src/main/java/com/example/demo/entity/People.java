package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = "numberAccount")
@ToString
public class People {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numberAccount;
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
    @OneToMany(mappedBy = "idRecipient")
    private List<Transaction> recipientTransaction;
    @OneToMany(mappedBy = "idSender")
    private List<Transaction> sendTransaction;
}
