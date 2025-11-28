package com.example.demo.web.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RequestTransactionCreate(

         @NotNull
         Long idSender,
         @NotNull
         Long idRecipient,
         @NotNull
         BigDecimal value
) {
}
