package com.example.demo.web.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RequestTransactionCreate(

         @NotNull
         int idSender,
         @NotNull
         int idRecipient,
         @NotNull
         BigDecimal value
) {
}
