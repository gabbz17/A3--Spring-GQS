package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "idSender")
    @JsonIgnore
    private People idSender;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "idRecipient")
    @JsonIgnore
    private People idRecipient;
    @NotNull
    private BigDecimal value;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dateTime;
}
