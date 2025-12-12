package com.example.boleto.api.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record BoletoPagamentoDto(
        @NotNull BigDecimal valorPago,
        @NotNull LocalDate dataPagamento
) {
}
