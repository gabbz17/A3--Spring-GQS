package com.example.demo.web.dto;

import java.math.BigDecimal;

public record ResponsePeopleDto(

        int numberAccount,
        String name,
        BigDecimal balance
) {
}